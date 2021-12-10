package com.test.app.service;

import com.test.app.entity.Post;

public interface PostCreationService {
    public boolean addPost(String key, String postText);
}
