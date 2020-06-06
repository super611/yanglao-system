CREATE TABLE rooms
(
    id          VARCHAR(36) not null,
    room        VARCHAR(50) not null,
    detail      VARCHAR(50) not null,
    price       double      not null,
    status      int          not null,
    deadline    DATETIME     not null,
    deadlinereserve    DATETIME     not null,
    PRIMARY KEY (id)
);
