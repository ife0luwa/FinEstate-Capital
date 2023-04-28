package dev.ifeoluwa.finestatecapitalapp.controller;

import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import dev.ifeoluwa.finestatecapitalapp.dto.LoginDTO;
import dev.ifeoluwa.finestatecapitalapp.dto.UserDTO;
import dev.ifeoluwa.finestatecapitalapp.service.ImageService;
import dev.ifeoluwa.finestatecapitalapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author on 24/04/2023
 * @project
 */

@RestController
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> signup(@RequestParam("name") String name, @RequestParam("email") String email,
                                    @RequestParam("phoneNumber") String phoneNumber,  @RequestParam("password") String password, @RequestParam("picture") MultipartFile picture) throws IOException {
        return userService.signUp(name, email, phoneNumber, password, picture);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
            return userService.login(loginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String email) {
        return userService.logout(email);
    }

}
