INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('Материал1', 1.25, 16, 1, 1);
INSERT INTO products (name, price, amount, source_id, unit_id) VALUES ('Материал2', 1.25, 16, 2, 2);

INSERT INTO incomings (amount, date, product_id) VALUES (16.25, '2016-01-01', 1);
INSERT INTO incomings (amount, date, product_id) VALUES (20.00, '2016-02-01', 2);

INSERT INTO outcomings (amount, date, product_id) VALUES (10.25, '2016-01-01', 1);
INSERT INTO outcomings (amount, date, product_id) VALUES (10.00, '2016-02-01', 2);