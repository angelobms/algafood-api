CREATE TABLE restaurant (
  id bigint NOT NULL AUTO_INCREMENT,
  address_cep varchar(9) DEFAULT NULL,
  address_complement varchar(60) DEFAULT NULL,
  address_district varchar(60) DEFAULT NULL,
  address_number varchar(20) DEFAULT NULL,
  address_street varchar(100) DEFAULT NULL,
  freight_rate decimal(10,2) NOT NULL,
  name varchar(80) NOT NULL,
  registration_date datetime NOT NULL,
  update_date datetime NOT NULL,
  address_city_id bigint DEFAULT NULL,
  kitchen_id bigint NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE restaurant ADD CONSTRAINT fk_restaurant_kitchen FOREIGN KEY (kitchen_id) REFERENCES kitchen (id);

ALTER TABLE restaurant ADD CONSTRAINT fk_restaurant_city_id FOREIGN KEY (address_city_id) REFERENCES city (id);