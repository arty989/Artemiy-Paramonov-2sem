CREATE TABLE users (
  user_id BIGSERIAL PRIMARY KEY,
  user_name VARCHAR(255) NOT NULL,
  user_password VARCHAR(255) NOT NULL,
  user_email VARCHAR(255) NOT NULL
);