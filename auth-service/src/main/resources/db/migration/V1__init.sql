CREATE SEQUENCE users_id_seq START 1;

CREATE TABLE IF NOT EXISTS users
(
    ID               uuid NOT NULL,
    TELEGRAM_USER_ID INTEGER,
    USERNAME         VARCHAR(45),
    NICK_NAME        VARCHAR(45),
    PASSWORD         VARCHAR(60),
    PRIMARY KEY (id)
);
