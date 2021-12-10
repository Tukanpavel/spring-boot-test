package com.test.app.controllers;

import com.test.app.entity.Post;
import com.test.app.service.PostListService;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/posts/")
public class PostListController {
  private final PostListService postListService;
  private static final Logger LOGGER = LoggerFactory.getLogger(PostListController.class);

  public PostListController(PostListService postListService) {
    this.postListService = postListService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<List<Post>> getPosts(@RequestBody MultiValueMap<String, String> map) {

    String key = map.getFirst("key");
    String filter = map.getFirst("filter");

    LOGGER.info("key:{}  filter:{}", key, filter);

    Optional<String> searchRequest = Optional.ofNullable(filter);
    Optional<List<Post>> posts = Optional.ofNullable(postListService.getPosts(key, searchRequest));

    if (posts.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<List<Post>>(posts.get(), HttpStatus.ACCEPTED);
  }
}
