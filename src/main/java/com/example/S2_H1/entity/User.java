package com.example.S2_H1.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
  UserId userId;
  String userName;
  String password;
  String email;
}
