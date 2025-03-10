CREATE TABLE articles (
  article_id SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  url VARCHAR(255) NOT NULL,
  category_id BIGINT NOT NULL,
  CONSTRAINT fk_category_article FOREIGN KEY (category_id)
    REFERENCES categories (category_id)
    ON DELETE CASCADE
);
