package com.software_project.pcbanabo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.software_project.pcbanabo.model.UserInfo;
import com.software_project.pcbanabo.repository.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userRepository;

    public UserInfoService(UserInfoRepository repo) {
        this.userRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new User(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public UserInfo getByEmail(String email) {
        return userRepository.findByEmail(email)
                   .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));
    }

    public UserInfo getById(Integer id) {
        return userRepository.findById(id)
                   .orElseThrow(() -> new UsernameNotFoundException("No user with id: " + id));
    }

    public List<UserInfo> getAllUsers() {
        return userRepository.findAll();
    }
    
}
