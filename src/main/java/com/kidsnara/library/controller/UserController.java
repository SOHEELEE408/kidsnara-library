package com.kidsnara.library.controller;

import com.kidsnara.library.dto.user.UserJoinResponse;
import com.kidsnara.library.service.UserServiceImpl;
import com.kidsnara.library.dto.user.UserJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponse> join(@RequestBody @Valid UserJoinRequest dto){

        UserJoinResponse user = userService.saveOrUpdateUser(dto.toEntity());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
