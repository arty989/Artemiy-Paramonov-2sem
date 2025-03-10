package com.example.S2_H1.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
  private UserId userId;
  private String userName;
  private String password;
  private String email;
}
