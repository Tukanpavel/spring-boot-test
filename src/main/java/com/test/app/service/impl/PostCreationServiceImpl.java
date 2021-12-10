package com.test.app.service.impl;

import com.test.app.entity.Post;
import com.test.app.repository.PostRepository;
import com.test.app.service.PostCreationService;
import com.test.app.service.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class PostCreationServiceImpl implements PostCreationService {

  private final PostRepository postRepository;
  private final WebService webService;
  private final Set<String> validCached = new HashSet<>();
  private final Logger LOGGER = LoggerFactory.getLogger(PostCreationServiceImpl.class);

  public PostCreationServiceImpl(PostRepository postRepository, WebService webService) {
    this.postRepository = postRepository;
    this.webService = webService;
  }

  @Override
  public boolean addPost(String key, String postText) {
    boolean isValid = webService.isValid(key);
    if (!validCached.contains(key) && !isValid) return false;

    validCached.add(key);

    Post post = new Post();
    post.setCreationDate(LocalDateTime.now());
    post.setContent(postText);
    post.setUser(webService.getUser(key));

    LOGGER.info("User:{}", post.getUser());

    postRepository.saveAndFlush(post);
    return true;
  }
}
