package com.test.app.service;

import com.test.app.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostListService {
    public List<Post> getPosts(String key, Optional<String> searchRequest);
}
