package com.test.app.service;

import com.test.app.entity.Users;

public interface WebService {
  public Boolean isValid(String key);

  public Users getUser(String Key);
}
