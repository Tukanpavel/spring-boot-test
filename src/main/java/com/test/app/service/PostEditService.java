package com.test.app.service;

import com.test.app.entity.Post;

public interface PostEditService {
    public boolean editPost(String key, Long id, String postContent);
}
