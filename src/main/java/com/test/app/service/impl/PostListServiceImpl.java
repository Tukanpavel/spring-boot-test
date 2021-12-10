package com.test.app.service.impl;

import com.test.app.entity.Post;
import com.test.app.repository.PostRepository;
import com.test.app.service.PostListService;
import com.test.app.service.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostListServiceImpl implements PostListService {
  private final PostRepository postRepository;
  private final WebService webService;
  private static final Logger LOGGER = LoggerFactory.getLogger(PostListServiceImpl.class);

  public PostListServiceImpl(PostRepository postRepository, WebService webService) {
    this.postRepository = postRepository;
    this.webService = webService;
  }

  @Override
  @Transactional
  public List<Post> getPosts(String key, Optional<String> searchRequest) {

    boolean isValid = webService.isValid(key);

    if (!isValid) return null;

    Stream<Post> posts = postRepository.findPosts();

    if (searchRequest.isEmpty()) {
      return posts.collect(Collectors.toList());
    }

    String request = searchRequest.get();

    return posts
        .filter(post -> post.getContent().contains(request))
        .collect(Collectors.toList());
  }
}
