package dev.ifeoluwa.finestatecapitalapp.controller;

import dev.ifeoluwa.finestatecapitalapp.dto.UserDTO;
import dev.ifeoluwa.finestatecapitalapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author on 24/04/2023
 * @project
 */

@RestController
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        return userService.signUp(userDTO);
    }
}
