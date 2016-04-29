--INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('Материал1', 1.25, 16, 1, 1);
--INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('Материал2', 1.25, 16, 2, 2);

INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('Material1', 1.25, 16, 1, 1);
INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('Material2', 1.25, 16, 2, 2);

INSERT INTO incomings (amount, date, product_id) VALUES (16.25, '2016-01-01', 1);
INSERT INTO incomings (amount, date, product_id) VALUES (16.25, '2016-01-01', 1);
INSERT INTO incomings (amount, date, product_id) VALUES (7.50, '2016-01-01', 1);
INSERT INTO incomings (amount, date, product_id) VALUES (20.00, '2016-02-01', 2);

INSERT INTO outcomings (amount, date, product_id) VALUES (10.25, '2016-01-01', 1);
INSERT INTO outcomings (amount, date, product_id) VALUES (10.00, '2016-02-01', 2);

--test remains
INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('Agar', 10, 12.5, 1, 1);

INSERT INTO incomings (amount, date, product_id) VALUES (5, '2016-01-01', 3);
INSERT INTO incomings (amount, date, product_id) VALUES (10, '2016-01-02', 3);
INSERT INTO incomings (amount, date, product_id) VALUES (10, '2016-01-05', 3);
INSERT INTO incomings (amount, date, product_id) VALUES (1.5, '2016-01-06', 3);
INSERT INTO incomings (amount, date, product_id) VALUES (5, '2016-01-08', 3);
INSERT INTO incomings (amount, date, product_id) VALUES (5, '2016-01-10', 3);

INSERT INTO outcomings (amount, date, product_id) VALUES (11, '2016-01-03', 3);
INSERT INTO outcomings (amount, date, product_id) VALUES (4, '2016-01-05', 3);
INSERT INTO outcomings (amount, date, product_id) VALUES (9, '2016-01-07', 3);

--test actreport
INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('OutcomingAgar1', 10, 4, 2, 1);
INSERT INTO incomings (amount, date, product_id) VALUES (50, '2016-01-01', 4);
INSERT INTO outcomings (amount, date, product_id) VALUES (11, '2016-03-03', 4);
INSERT INTO outcomings (amount, date, product_id) VALUES (4.5, '2016-03-05', 4);
INSERT INTO outcomings (amount, date, product_id) VALUES (9, '2016-03-07', 4);
INSERT INTO outcomings (amount, date, product_id) VALUES (11, '2016-04-11', 4);
INSERT INTO outcomings (amount, date, product_id) VALUES (1.5, '2016-04-05', 4);
INSERT INTO outcomings (amount, date, product_id) VALUES (9, '2016-04-07', 4);

INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('OutcomingAgar2', 10, 12.5, 2, 1);

INSERT INTO incomings (amount, date, product_id) VALUES (50, '2016-01-01', 5);
INSERT INTO outcomings (amount, date, product_id) VALUES (6, '2016-04-03', 5);
INSERT INTO outcomings (amount, date, product_id) VALUES (4, '2016-04-05', 5);
INSERT INTO outcomings (amount, date, product_id) VALUES (9, '2016-04-07', 5);
INSERT INTO outcomings (amount, date, product_id) VALUES (1.1, '2016-05-03', 5);
INSERT INTO outcomings (amount, date, product_id) VALUES (4, '2016-05-05', 5);
INSERT INTO outcomings (amount, date, product_id) VALUES (9, '2016-05-07', 5);