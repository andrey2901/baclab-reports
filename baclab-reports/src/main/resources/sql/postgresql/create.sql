CREATE TABLE units
(
   id bigserial PRIMARY KEY,
   unit text NOT NULL,
   UNIQUE (unit)
);

INSERT INTO units(unit) VALUES ('амп'), ('флак'), ('шт'), ('гр'), ('кг'), ('набір'), ('уп'), ('кор'), ('пар');

CREATE TABLE sources
(
	id bigserial PRIMARY KEY,
	source text NOT NULL,
	UNIQUE (source)
);

INSERT INTO sources(source) VALUES ('Реактиви, поживні середовища'), ('Меценат'), ('Від провізора'), ('Від дезінфектора');

CREATE TABLE products
(
	id bigserial PRIMARY KEY,
	name text NOT NULL,
	price double precision NOT NULL,
	amount double precision NOT NULL,
	source_id bigint NOT NULL,
	unit_id bigint NOT NULL,
	UNIQUE (name, price, source_id, unit_id),
	FOREIGN KEY (source_id) REFERENCES sources(id),
	FOREIGN KEY (unit_id) REFERENCES units(id)
);

CREATE TABLE incomings
(
	id bigserial PRIMARY KEY,
	amount double precision NOT NULL,
	date date  NOT NULL,
	product_id bigint NOT NULL,
	FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE outcomings
(
	id bigserial PRIMARY KEY,
	amount double precision NOT NULL,
	date date NOT NULL,
	product_id bigint NOT NULL,
	FOREIGN KEY (product_id) REFERENCES products(id)
);