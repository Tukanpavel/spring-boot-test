package com.test.app.controllers;

import com.test.app.entity.Users;
import com.test.app.service.KeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path = "/key")
public class KeyController {

  private static final Logger LOGGER = LoggerFactory.getLogger(KeyController.class);
  private final KeyService keyService;

  public KeyController(KeyService keyService) {
    this.keyService = keyService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<String> getKey(@RequestBody MultiValueMap<String, String> payload) {

    String username = payload.getFirst("username");
    String password = payload.getFirst("password");

    LOGGER.info("username:{} password:{}", username, password);

    Optional<String> key = Optional.ofNullable(keyService.getKey(username, password));

    if (key.isEmpty()) return new ResponseEntity<>("Wrong Credentials", HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(key.get(), HttpStatus.ACCEPTED);
  }

  @PostMapping(
      path = "/validate",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<String> isValid(@RequestBody MultiValueMap<String, String> keyMap) {

    String key = keyMap.getFirst("key");

    LOGGER.info("key:{}", key);

    boolean isValid = keyService.isValid(key);

    if (!isValid) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @PostMapping(path = "/user", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Users> getUserByKey(@RequestBody MultiValueMap<String, String> keyMap) {
    String key = keyMap.getFirst("key");

    Optional<Users> user = keyService.getUserFromKey(key);

    if (user.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(user.get(), HttpStatus.ACCEPTED);
  }
}
