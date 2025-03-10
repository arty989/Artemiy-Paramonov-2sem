CREATE TABLE categories (
  category_id SERIAL PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT fk_user_category FOREIGN KEY (user_id)
    REFERENCES users (user_id)
    ON DELETE CASCADE
);
