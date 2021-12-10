package com.test.app.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  private String username;

  private String password;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Post> posts = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Users users = (Users) o;
    return id != null && Objects.equals(id, users.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
