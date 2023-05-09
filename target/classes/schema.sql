--DROP TABLE IF EXISTS COM_USER;

create TABLE IF NOT EXISTS COM_USER (
  id       int unsigned primary key not null auto_increment,
  name     varchar(32)              not null,
  password varchar(32)               null,
  state    tinyint                   null,
  address  varchar(128)              null,
  email    varchar(32)               null
);