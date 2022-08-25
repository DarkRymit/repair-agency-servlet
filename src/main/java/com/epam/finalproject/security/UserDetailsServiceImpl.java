package com.epam.finalproject.security;


import com.epam.finalproject.framework.beans.annotation.Component;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.UserDetailsService;
import com.epam.finalproject.framework.security.UsernameNotFoundException;
import com.epam.finalproject.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserDetailsImpl.of(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cant found " + username)));
    }
}
