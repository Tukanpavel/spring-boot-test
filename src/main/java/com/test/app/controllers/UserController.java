package com.test.app.controllers;

import com.test.app.entity.Users;
import com.test.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping(path = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<String> createUser(@RequestBody MultiValueMap<String, String> credentials) {
    String username = credentials.getFirst("username");
    String password = credentials.getFirst("password");

    LOGGER.info("username:{} password:{}", username, password);

    Optional<Users> userFromDatabase =
        Optional.ofNullable(userRepository.findUsersByUsername(username));

    if (userFromDatabase.isPresent()) {
      return new ResponseEntity<>("Username is already in use!", HttpStatus.BAD_REQUEST);
    }

    Users user = new Users();
    user.setUsername(username);
    user.setPassword(password);

    userRepository.saveAndFlush(user);

    return new ResponseEntity<>("User is successfully created.", HttpStatus.ACCEPTED);
  }
}
