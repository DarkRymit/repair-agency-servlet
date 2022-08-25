package com.epam.finalproject.service.impl;

import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.exceptions.SingUpException;
import com.epam.finalproject.framework.data.transaction.PlatformTransactionManager;
import com.epam.finalproject.framework.data.transaction.TransactionStatus;
import com.epam.finalproject.framework.security.password.PasswordEncoder;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.Role;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.enums.RoleEnum;
import com.epam.finalproject.repository.RoleRepository;
import com.epam.finalproject.repository.UserRepository;
import com.epam.finalproject.request.SignUpRequest;
import com.epam.finalproject.service.UserService;
import com.epam.finalproject.util.UserUtil;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    PlatformTransactionManager transactionManager;

    ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, PlatformTransactionManager transactionManager, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.transactionManager = transactionManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public User signUpNewUserAccount(SignUpRequest form) {
        TransactionStatus ts = transactionManager.getTransaction();
        User result;
        Role unverified = roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow();
        Role customer = roleRepository.findByName(RoleEnum.CUSTOMER).orElseThrow();
        try {
            User user = UserUtil.createWithInitializedContainers();
            modelMapper.map(form, user);
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.addRole(unverified);
            user.addRole(customer);
            user.setCreationDate(Instant.now());
            user.setLastModifiedDate(Instant.now());
            userRepository.save(user);
            roleRepository.addRoleForUser(unverified, user);
            roleRepository.addRoleForUser(customer, user);
            result = user;
        } catch (Exception e) {
            transactionManager.rollback(ts);
            throw new SingUpException(e);
        }
        transactionManager.commit(ts);
        return result;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO findById(Long id) {
        return constructDTO(userRepository.findById(id).orElseThrow());
    }

    public UserDTO constructDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
