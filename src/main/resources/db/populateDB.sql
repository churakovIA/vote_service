DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM menu;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', '{noop}password'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO restaurants (name) VALUES
   ('Нихао'),
   ('Mama Roma'),
   ('Корюшка'),
   ('Тако');

INSERT INTO menu (date, restaurant_id) VALUES
('2018-12-11', 100002),
('2018-12-11', 100003),
(TODAY(), 100002),
(TODAY(), 100003);

INSERT INTO dishes (name, price, menu_id) VALUES
   ('БАРАНЬИ РЁБРЫШКИ ПО-ХУНАНЬСКИ', 78000, 100006),
   ('Паста Делла Мамма', 39500, 100007),
   ('ЦЫПЛЕНОК, ТОМЛЕННЫЙ В ИМБИРНОМ СОУСЕ', 68000, 100008),
   ('Судак Портофино с соусом песто', 42500, 100009);

INSERT INTO votes (date, menu_id, user_id) VALUES
('2018-12-11', 100006, 100000)