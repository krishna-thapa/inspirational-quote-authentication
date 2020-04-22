# --- !Ups

--- they all have to be lower case
CREATE TABLE USERINFO(
  USER_ID      serial PRIMARY KEY,
  FIRST_NAME   varchar(100),
  LAST_NAME    varchar(100),
  EMAIL        varchar(100),
  PROVIDER_ID  varchar(100) NOT NULL,
  PROVIDER_KEY varchar(100) NOT NULL
);

CREATE TABLE PASSWORD (
  PROVIDER_KEY VARCHAR(254) NOT NULL PRIMARY KEY,
  HASHER       varchar(100)         NOT NULL,
  HASH         varchar(100)         NOT NULL,
  SALT         varchar(100)
);

# --- !Downs

DROP TABLE USERINFO;
DROP TABLE PASSWORD;