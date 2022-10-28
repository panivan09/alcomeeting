


INSERT INTO users(name, last_name, email, user_name, password) VALUES
( 'Matthew', 'McConaughey', 'matthew@gmail.com', 'mat', '$2a$10$iGLpYS5hj2pcb4AX3bXB../pUH0JNM6k1tz0TlnhASz/ZoLCssVP.'),
( 'Keanu', 'Reeves', 'keanu@mail.com', 'keanu', '$2a$10$iGLpYS5hj2pcb4AX3bXB../pUH0JNM6k1tz0TlnhASz/ZoLCssVP.'),
( 'Johnny', 'Depp', 'johnny.gmail.com', 'johnny', '$2a$10$iGLpYS5hj2pcb4AX3bXB../pUH0JNM6k1tz0TlnhASz/ZoLCssVP.'),
( 'Eva', 'Mendes', 'eva.gmail.com', 'eva', '$2a$10$iGLpYS5hj2pcb4AX3bXB../pUH0JNM6k1tz0TlnhASz/ZoLCssVP.'),
( 'Lindsay', 'Lohan', 'lindsay.gmail.com', 'lin', '$2a$10$iGLpYS5hj2pcb4AX3bXB../pUH0JNM6k1tz0TlnhASz/ZoLCssVP.');

------------------------------------------------------------------------------------------------------------------------

INSERT INTO beverages(name, description) VALUES
('Gin', 'England'),
('Whiskey','Irish'),
('Vodka', 'babushka Zina'),
('Rum', 'Captain Morgan'),
('Beer', 'Germany'),
('Tequila', 'Mexico'),
('Champagne', 'France'),
('Wine', 'France');

------------------------------------------------------------------------------------------------------------------------

INSERT INTO meetings(date, address, name, owner_user_id) VALUES
('2020-05-17', 'Dmytro Yavornytsky Avenue 3', 'Do usra4ki', 4),
('2020-07-25','Sholom-Aleichem St, 4/26', 'Shabbat wine party', 5),
('2020-11-11', 'in the house', 'Friends party', 1);

------------------------------------------------------------------------------------------------------------------------
INSERT INTO users_beverages(user_id, beverage_id) VALUES
(1, 5),
(1, 2),
(1, 3),
(2, 2),
(2, 5),
(2, 1),
(3, 2),
(3, 6),
(3, 4),
(4, 3),
(4, 6),
(4, 2),
(5, 5),
(5, 7),
(5, 8);

------------------------------------------------------------------------------------------------------------------------
INSERT INTO meetings_users(meeting_id, user_id) VALUES
(1, 4),
(1, 2),
(1, 3),
(2, 5),
(3, 1),
(3, 5),
(3, 2);


------------------------------------------------------------------------------------------------------------------------
INSERT INTO meetings_beverages(meeting_id, beverage_id) VALUES
(1, 3),
(1, 2),
(2, 5),
(3, 7),
(3, 5),
(3, 2);

------------------------------------------------------------------------------------------------------------------------