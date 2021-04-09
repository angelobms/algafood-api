CREATE TABLE restaurant_payment_method (
  restaurant_id bigint NOT NULL,
  payment_method_id bigint NOT NULL,
  PRIMARY KEY (restaurant_id, payment_method_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE restaurant_payment_method ADD CONSTRAINT fk_restaurant_payment_method_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE restaurant_payment_method ADD CONSTRAINT fk_restaurant_payment_method_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method (id);


