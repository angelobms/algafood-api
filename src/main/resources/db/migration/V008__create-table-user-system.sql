CREATE TABLE user_system (
  id bigint NOT NULL AUTO_INCREMENT,
  email varchar(255) NOT NULL,
  name varchar(80) NOT NULL,
  password varchar(255) NOT NULL,
  registration_date datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
