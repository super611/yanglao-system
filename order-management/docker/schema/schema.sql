CREATE TABLE orders
(
    id         VARCHAR(36) NOT null,
    userid     int(36)     NOT null,
    username   VARCHAR(36) NOT NULL,
    roomid     VARCHAR(36) NOT NULL,
    term       int     NOT NULL,
    sum        double     NOT NULL,
    datetime   DATETIME  NOT NULL,
    status     int (36)    NOT NULL,
    PRIMARY KEY (id)
);
