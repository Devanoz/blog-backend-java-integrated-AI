CREATE TABLE category
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    slug       VARCHAR(100) NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    updated_at BIGINT,
    created_at BIGINT       NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;