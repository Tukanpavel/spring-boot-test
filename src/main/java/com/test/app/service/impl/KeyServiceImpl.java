package com.test.app.service.impl;

import com.test.app.entity.Users;
import com.test.app.repository.UserRepository;
import com.test.app.service.KeyService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KeyServiceImpl implements KeyService {

  private final UserRepository userRepository;

  public KeyServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public String getKey(String username, String password) {
    boolean present =
        Optional.ofNullable(userRepository.getUserByUsernameAndPassword(username, password))
            .isPresent();
    if (present) return createKey(username, password);
    return null;
  }

  @Override
  public boolean isValid(String key) {
    String[] credentials = parseKey(key);
    return Optional.ofNullable(
            userRepository.getUserByUsernameAndPassword(credentials[0], credentials[1]))
        .isPresent();
  }

  private String[] parseKey(String key) {
    return key.split("_");
  }

  @Override
  public Optional<Users> getUserFromKey(String key) {
    String[] keys = parseKey(key);
    return Optional.ofNullable(userRepository.findUsersByUsername(keys[0]));
  }

  private String createKey(String username, String password) {
    return new StringBuffer(username).append('_').append(password).toString();
  }
}
