DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS urls;
DROP TABLE IF EXISTS user_roles;
DROP TYPE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id           INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name         VARCHAR                           NOT NULL,
    email        VARCHAR UNIQUE                    NOT NULL,
    password     VARCHAR                           NOT NULL,
    registered   TIMESTAMP           DEFAULT now() NOT NULL,
    urls_created INT                 DEFAULT 0     NOT NULL,
    max_urls     INT                               NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TYPE user_role as ENUM ('PREMIUM', 'ADMIN');

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    user_role,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE urls
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    user_id   INTEGER NOT NULL,
    created   TIMESTAMP           DEFAULT now(),
    long_url  TEXT    NOT NULL,
    short_url TEXT    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_user_datetime_idx ON urls (user_id, short_url);