package com.test.app.controllers;

import com.test.app.entity.Post;
import com.test.app.service.PostEditService;
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
public class PostEditController {
  private final PostEditService postEditService;
  private static final Logger LOGGER = LoggerFactory.getLogger(PostEditController.class);

  public PostEditController(PostEditService postEditService) {
    this.postEditService = postEditService;
  }

  @PostMapping(path = "/edit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Post> editPost(@RequestBody MultiValueMap<String, String> keyMap) {

    String key = keyMap.getFirst("key");
    String id = keyMap.getFirst("id");
    String postContent = keyMap.getFirst("postContent");
    if (keyMap.getFirst("id") == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    LOGGER.info("key:{} post:{} id:{}", key, postContent, id);

    boolean isSuccess = postEditService.editPost(key, Long.parseLong(id), postContent);

    if (!isSuccess) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
