package com.test.app.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "post")
// @NamedQuery(name = "Post.findPostsByUserId")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  private Users user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  private LocalDateTime creationDate;

  private LocalDateTime editDate;

  private boolean isEdited;

  private String content;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Post post = (Post) o;
    return id != null && Objects.equals(id, post.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
