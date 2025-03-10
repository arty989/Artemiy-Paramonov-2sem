INSERT INTO users (user_name, user_password, user_email)
VALUES ("random_user", "12345678", "random@example.com")
RETURNING user_id INTO random_user_id;

INSERT INTO categories (category_name, user_id)
VALUES ("Random Category", random_user_id)
RETURNING category_id INTO random_category_id;

INSERT INTO articles (title, url, category_id)
VALUES ("MTS", "https://www.mts.ru/", random_category_id);