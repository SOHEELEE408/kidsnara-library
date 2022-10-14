package com.kidsnara.library.service;

import com.kidsnara.library.domain.user.User;
import com.kidsnara.library.dto.user.UserJoinResponse;
import com.kidsnara.library.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Override
    CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserJoinResponse saveOrUpdateUser(User user);
}
