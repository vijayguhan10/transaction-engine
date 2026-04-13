# --- !Ups

CREATE TABLE IF NOT EXISTS userinfo (
  usrid     VARCHAR(64) PRIMARY KEY,
  emailid   VARCHAR(255) NOT NULL,
  password  VARCHAR(255) NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS userinfo;
