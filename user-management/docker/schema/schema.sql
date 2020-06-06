CREATE TABLE users
(
    id           int not null,
    password     VARCHAR(50) not null,
    name         VARCHAR(50) not null,
    phone        VARCHAR(50) not null,
    address      VARCHAR(50) not null,
    PRIMARY KEY (id)
);
