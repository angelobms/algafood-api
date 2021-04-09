CREATE TABLE city (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(80) NOT NULL,
  state_id bigint NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE city ADD CONSTRAINT fk_city_state FOREIGN KEY (state_id) REFERENCES state (id);
