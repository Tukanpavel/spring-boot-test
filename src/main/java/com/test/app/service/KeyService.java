package com.test.app.service;


import com.test.app.entity.Users;

import java.util.Optional;

public interface KeyService {
    public String getKey(String username, String password);
    public boolean isValid(String key);
    public Optional<Users> getUserFromKey(String key);
}
