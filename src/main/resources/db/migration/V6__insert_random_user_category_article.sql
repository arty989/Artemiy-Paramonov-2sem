WITH new_user AS (
  INSERT INTO users (user_name, user_password, user_email)
  VALUES ('random_user', '12345678', 'random@example.com')
  RETURNING user_id
),
new_category AS (
  INSERT INTO categories (category_name, user_id)
  SELECT 'Random Category', user_id FROM new_user
  RETURNING category_id
)
INSERT INTO articles (title, url, category_id)
SELECT 'MTS', 'https://www.mts.ru/', category_id FROM new_category;