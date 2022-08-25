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
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        try {
            User user = getSetUpAndSave(UserUtil::createWithInitializedContainers, u -> {
                modelMapper.map(form, u);
                u.setPassword(passwordEncoder.encode(form.getPassword()));
                u.addRole(roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow());
                u.setCreationDate(Instant.now());
                u.setLastModifiedDate(Instant.now());
            });
            roleRepository.addRoleForUser(roleRepository.findByName(RoleEnum.UNVERIFIED).orElseThrow(),user);
            roleRepository.addRoleForUser(roleRepository.findByName(RoleEnum.CUSTOMER).orElseThrow(),user);
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
    public boolean isUserHaveRoleWithName(User user, RoleEnum roleName) {
        return user.getRoles().stream().map(Role::getName).anyMatch(eRole -> eRole.equals(roleName));
    }

    @Override
    public boolean isUserNotVerified(User user) {
        return isUserHaveRoleWithName(user,RoleEnum.UNVERIFIED);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO findById(Long id) {
        return constructDTO(userRepository.findById(id).orElseThrow());
    }

    private User getSetUpAndSave(Supplier<User> userSupplier, Consumer<User> userConsumer) {
        User user = userSupplier.get();
        userConsumer.accept(user);
        userRepository.save(user);
        return user;
    }

    public UserDTO constructDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

}
