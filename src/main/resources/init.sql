CREATE TABLE IF NOT EXISTS BOOKS
(
    id       BIGINT PRIMARY KEY auto_increment,
    name     character varying(255) NOT NULL,
    language character varying(255) NOT NULL,
    category character varying(255) NOT NULL
);