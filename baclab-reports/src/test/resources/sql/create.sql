CREATE TABLE units
(
	id int not null auto_increment,
	unit varchar(100) not null,
	PRIMARY KEY (id),
	UNIQUE (unit)
);

INSERT INTO units(unit) VALUES ('амп'), ('флак'), ('шт'), ('гр'), ('кг'), ('набір'), ('уп'), ('кор'), ('пар');

CREATE TABLE sources
(
	id int not null auto_increment,
	source varchar(100) not null,
	PRIMARY KEY (id),
	UNIQUE (source)
);

INSERT INTO sources(source) VALUES ('Реактиви, поживні середовища'), ('Меценат'), ('Від провізора'), ('Від дезінфектора');

CREATE TABLE products
(
	id int not null auto_increment,
	name varchar(255) not null,
	price double not null,
	amount double not null,
	source_id int not null,
	unit_id int not null,
	PRIMARY KEY (id),
	UNIQUE (name, price, source_id, unit_id),
	FOREIGN KEY (source_id) REFERENCES sources(id),
	FOREIGN KEY (unit_id) REFERENCES units(id)
);

CREATE TABLE incomings
(
	id int not null auto_increment,
	amount double not null,
	date date not null,
	product_id int not null,
	PRIMARY KEY (id),
	FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE outcomings
(
	id int not null auto_increment,
	amount double not null,
	date date not null,
	product_id int not null,
	PRIMARY KEY (id),
	FOREIGN KEY (product_id) REFERENCES products(id)
);