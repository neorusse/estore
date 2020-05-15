package com.den.estore.io.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue
  private long id;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, length = 120)
  private String email;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String encryptedPassword;

  @Column(insertable = true, updatable = false)
  private LocalDateTime created;

  private LocalDateTime modified;

  @PrePersist
  void onCreate() {

    this.setCreated(LocalDateTime.now());
    this.setModified(LocalDateTime.now());
  }

  @PreUpdate
  void onUpdate() {
    this.setModified(LocalDateTime.now());
  }

}
