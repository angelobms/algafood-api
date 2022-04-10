set foreign_key_checks = 0;

delete from kitchen;
delete from state;
delete from city;
delete from payment_method;
delete from group_system;
delete from permission;
delete from group_permission;
delete from user_system;
delete from user_group;
delete from restaurant;
delete from restaurant_payment_method;
delete from product;

set foreign_key_checks = 1;

alter table kitchen auto_increment = 1;
alter table state auto_increment = 1;
alter table city auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table group_system auto_increment = 1;
alter table permission auto_increment = 1;
alter table user_system auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table product auto_increment = 1;
alter table order_restaurant auto_increment = 1;
alter table order_item auto_increment = 1;

insert into kitchen (id, name) values (1, 'Tailandesa');
insert into kitchen (id, name) values (2, 'Indiana');
insert into kitchen (id, name) values (3, 'Argentina');
insert into kitchen (id, name) values (4, 'Brasileira');
insert into kitchen (id, name) values (5, 'Americana');
insert into kitchen (id, name) values (6, 'Japonesa');

insert into state (id, name) values (1, 'Minas Gerais');
insert into state (id, name) values (2, 'São Paulo');
insert into state (id, name) values (3, 'Ceará');

insert into city (id, name, state_id) values (1, 'Uberlândia', 1);
insert into city (id, name, state_id) values (2, 'Belo Horizonte', 1);
insert into city (id, name, state_id) values (3, 'São Paulo', 2);
insert into city (id, name, state_id) values (4, 'Campinas', 2);
insert into city (id, name, state_id) values (5, 'Fortaleza', 3);

insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date, address_city_id, address_cep, address_street, address_number, address_complement, address_district) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '42717-860', 'Rua Marta Aguiar Da silva', '174', 'Apto 104, Torre 2', 'Vida Nova');
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date) values (5, 'Snack bar of Uncle Sam', 11, 4, utc_timestamp, utc_timestamp);
insert into restaurant (id, name, freight_rate, kitchen_id, registration_date, update_date) values (6, 'Bar of Maria', 6, 4, utc_timestamp, utc_timestamp);

insert into payment_method (id, description) values (1, 'Credit card');
insert into payment_method (id, description) values (2, 'Debit card');
insert into payment_method (id, description) values (3, 'Cash');

insert into permission (id, name, description) values (1, 'CONSULT_KITCHENS', 'Allows you to consult kitchens');
insert into permission (id, name, description) values (2, 'EDIT_KITCHENS', 'Allows you to edit kitchens');

insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into product (name, description, price, active, restaurant_id) values ('Pork with sweet and sour sauce', 'Delicious pork meat with special sauce', 78.90, 0, 1);
insert into product (name, description, price, active, restaurant_id) values ('Thai shrimp', '16 large prawns in hot sauce', 110, 1, 1);
insert into product (name, description, price, active, restaurant_id) values ('Spicy salad with grilled meat', 'Leaf salad with fine cuts of grilled beef and our special red pepper sauce', 87.20, 1, 2);
insert into product (name, description, price, active, restaurant_id) values ('Garlic Naan', 'Traditional Indian bread with garlic topping', 21, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Murg Curry', 'Chicken cubes prepared with curry sauce and spices', 43, 1, 3);
insert into product (name, description, price, active, restaurant_id) values ('Ancho Steak', 'Soft and juicy cut, with two fingers thick, removed from the front of the meatloaf', 79, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('T-Bone', 'Very tasty cut, with a T-shaped bone, with the tenderloin on one side and the tenderloin on the other', 89, 1, 4);
insert into product (name, description, price, active, restaurant_id) values ('X-Tudo sandwich', 'Sandwich with lots of cheese, beef hamburger, bacon, egg, salad and mayonnaise', 19, 1, 5);
insert into product (name, description, price, active, restaurant_id) values ('Termite Skewer', 'Accompanies flour, cassava and vinaigrette', 8, 1, 6);

insert into group_system (id, name) values (1, 'Manager'), (2, 'Seller'), (3, 'Secretary'), (4, 'Recorder');

insert into group_permission (group_id, permission_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1); 

insert into user_system (id, name, email, password, registration_date) values
(1, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp);  

delete from restaurant_responsible_user;

insert into restaurant_responsible_user (restaurant_id, user_id) values (1, 5), (3, 5);

delete from order_item;
delete from order_restaurant;

insert into order_restaurant (id, xtid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_cep, address_street, address_number, address_complement, address_district, status, registration_date, sub_total, freight_rate, total_value)
values (1, 'd5bd7340-a772-45de-adca-e46944d833fb', 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CRIETED', utc_timestamp, 298.90, 10, 308.90);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, observation)
values (1, 1, 1, 1, 78.9, 78.9, null);


insert into order_item (id, order_id, product_id, amount, unit_price, total_price, observation)
values (2, 1, 2, 2, 110, 220, 'Less spicy please');

insert into order_restaurant (id, xtid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_cep, address_street, address_number, address_complement, address_district, status, registration_date, sub_total, freight_rate, total_value)
values (2, '98288f5e-88cc-47e6-8ab6-b18538dd5892', 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'CRIETED', utc_timestamp, 79, 0, 79);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, observation)
values (3, 2, 6, 1, 79, 79, 'To the point');


insert into order_restaurant (id, xtid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_cep, address_street, address_number, address_complement, address_district, status, registration_date, confirmation_date, delivery_date, sub_total, freight_rate, total_value)
values (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil', 'DELIVERED', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, observation)
values (4, 3, 2, 1, 110, 110, null);


insert into order_restaurant (id, xtid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_cep, address_street, address_number, address_complement, address_district, status, registration_date, confirmation_date, delivery_date, sub_total, freight_rate, total_value)
values (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro', 'DELIVERED', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, observation)
values (5, 4, 3, 2, 87.2, 174.4, null);


insert into order_restaurant (id, xtid, restaurant_id, customer_user_id, payment_method_id, address_city_id, address_cep, address_street, address_number, address_complement, address_district, status, registration_date, confirmation_date, delivery_date, sub_total, freight_rate, total_value)
values (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins', 'DELIVERED', '2019-11-03 02:00:30', '2019-11-03 02:01:21', '2019-11-03 02:20:10', 87.2, 10, 97.2);

insert into order_item (id, order_id, product_id, amount, unit_price, total_price, observation)
values (6, 5, 3, 1, 87.2, 87.2, null);










