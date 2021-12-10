package com.test.app.controllers;

import com.test.app.entity.Post;
import com.test.app.service.PostCreationService;
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

@Controller
@RequestMapping(path = "/posts")
public class PostCreationController {
  private final PostCreationService postCreationService;
  private static final Logger LOGGER = LoggerFactory.getLogger(PostCreationController.class);

  public PostCreationController(PostCreationService postCreationService) {
    this.postCreationService = postCreationService;
  }

  @PostMapping(path = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Post> createPost(
      @RequestBody MultiValueMap<String, String> keyMap) {

    String key = keyMap.getFirst("key");
    String postContent = keyMap.getFirst("postContent");

    LOGGER.info("key:{} post:{}", key, postContent);

    boolean isSuccess = postCreationService.addPost(key, postContent);

    if (!isSuccess) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
