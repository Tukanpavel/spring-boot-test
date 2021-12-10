package com.test.app.repository;

import com.test.app.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, Long> {
  @Query("select u from Users u where u.username = ?1 and u.password = ?2")
  public Users getUserByUsernameAndPassword(String username, String password);

  public Users findUsersByUsername(String username);

  @Query("select u.id from Users u where u.username=?1")
  public Long getIdByUsername(String username);
}
