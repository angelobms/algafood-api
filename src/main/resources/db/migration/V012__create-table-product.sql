CREATE TABLE product (
  id bigint NOT NULL AUTO_INCREMENT,
  active bit(1) NOT NULL,
  description text NOT NULL,
  name varchar(80) NOT NULL,
  price decimal(10,2) NOT NULL,
  restaurant_id bigint NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE product ADD CONSTRAINT fk_product_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);
