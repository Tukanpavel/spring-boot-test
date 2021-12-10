package com.test.app.service.impl;

import com.test.app.entity.Post;
import com.test.app.repository.PostRepository;
import com.test.app.service.PostEditService;
import com.test.app.service.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostEditServiceImpl implements PostEditService {
  private final PostRepository postRepository;
  private final WebService webService;
  private final Logger LOGGER = LoggerFactory.getLogger(PostEditServiceImpl.class);

  public PostEditServiceImpl(PostRepository postRepository, WebService webService) {
    this.postRepository = postRepository;
    this.webService = webService;
  }

  @Override
  public boolean editPost(String key, Long id, String postContent) {
    boolean isValid = webService.isValid(key);
    if (!isValid) return false;

    Optional<Post> postFromDatabase = Optional.ofNullable(postRepository.findPostById(id));

    if(postFromDatabase.isEmpty()) return false;

    Post post = postFromDatabase.get();
    post.setContent(postContent);
    post.setEdited(true);
    post.setEditDate(LocalDateTime.now());

    LOGGER.info("post:{}", post.isEdited());

    postRepository.saveAndFlush(post);
    return true;
  }
}
