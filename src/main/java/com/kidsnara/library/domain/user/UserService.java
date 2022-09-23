package com.kidsnara.library.domain.user;

import com.kidsnara.library.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Override
    CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User saveOrUpdateUser(User user);
}
