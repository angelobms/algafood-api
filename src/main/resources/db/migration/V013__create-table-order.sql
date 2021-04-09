CREATE TABLE order_restaurant (
  id bigint NOT NULL AUTO_INCREMENT,
  sub_total decimal(10,2) NOT NULL,
  freight_rate decimal(10,2) NOT NULL,
  total_value decimal(10,2) NOT NULL,
  restaurant_id bigint NOT NULL,
  customer_user_id bigint NOT NULL,
  payment_method_id bigint NOT NULL,
  
  address_city_id bigint DEFAULT NULL,
  address_cep varchar(9) DEFAULT NULL,
  address_complement varchar(60) DEFAULT NULL,
  address_district varchar(60) DEFAULT NULL,
  address_number varchar(20) DEFAULT NULL,
  address_street varchar(100) DEFAULT NULL,
  
  status varchar(10) NOT NULL,
  registration_date datetime NOT NULL,
  confirmation_date datetime NULL,
  cancellation_date datetime NULL,
  delivery_date datetime NULL,
  
  PRIMARY KEY (id),
  
  CONSTRAINT fk_order_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id),
  CONSTRAINT fk_order_customer_user FOREIGN KEY (customer_user_id) REFERENCES user_system (id),
  CONSTRAINT fk_order_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_method (id)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

