CREATE TABLE user_site (
  user_id BIGINT NOT NULL,
  site_id BIGINT NOT NULL,
  CONSTRAINT fk_user_site_user FOREIGN KEY (user_id)
    REFERENCES users (user_id)
    ON DELETE CASCADE,
  CONSTRAINT fk_user_site_site FOREIGN KEY (site_id)
    REFERENCES sites (site_id)
    ON DELETE CASCADE,
  PRIMARY KEY (user_id, site_id)
);
