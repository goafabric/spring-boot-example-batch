DROP TABLE IF EXISTS people;

CREATE TABLE people  (
   	id varchar(36) not null
		constraint pk_people
			primary key,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

CREATE TABLE toy_catalog (
   	id varchar(36) not null
		constraint pk_toys
			primary key,
    toy_name VARCHAR(20),
    price VARCHAR(20)
);