package com.jobfind.config;

import com.jobfind.models.User;
import com.jobfind.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException ("User not found with email: " + email));

        return  org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(passwordEncoder.encode(user.getPasswordHash()))
                .roles(String.valueOf(user.getRole()))
                .build();
    }
}