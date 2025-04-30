package com.khaled.bank_management_system.service;

import com.khaled.bank_management_system.api.ApiException;
import com.khaled.bank_management_system.model.User;
import com.khaled.bank_management_system.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  authRepository.findUserByUsername(username);
        if(user == null) {
            throw new ApiException("Wrong username or password");
        }

        return user;
    }
}
