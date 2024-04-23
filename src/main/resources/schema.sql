CREATE TABLE IF NOT EXISTS books
(
    id       BIGINT PRIMARY KEY auto_increment,
    name     VARCHAR(255) NOT NULL,
    language VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL
);