package com.test.app.repository;

import com.test.app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface PostRepository extends JpaRepository<Post, Long> {
  @Query("select p from Post p")
  public Stream<Post> findPosts();
  public Post findPostById(Long id);
}
