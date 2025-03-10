CREATE TABLE Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    age INT CHECK (age > 0)
);

CREATE TABLE Sites (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    userId INT NOT NULL,
    categories VARCHAR(255),
    name VARCHAR(255) NOT NULL
);
