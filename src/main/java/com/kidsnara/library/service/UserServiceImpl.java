package com.kidsnara.library.service;

import com.kidsnara.library.config.exceptionhandler.BaseErrorResult;
import com.kidsnara.library.config.exceptionhandler.BaseException;
import com.kidsnara.library.domain.user.User;
import com.kidsnara.library.dto.user.UserJoinResponse;
import com.kidsnara.library.repository.UserRepository;
import com.kidsnara.library.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("not found User by Email : " + email));

        return new CustomUserDetails(user);
    }

    @Override
    public UserJoinResponse saveOrUpdateUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new BaseException(BaseErrorResult.DUPLICATED_USER_REGISTER);
        }
        user.encodePassword(this.passwordEncoder);
        User savedUser = this.userRepository.save(user);

        return UserJoinResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }
}
