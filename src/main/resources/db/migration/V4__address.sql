-- separate schema
CREATE SCHEMA IF NOT EXISTS masterdata;

create table masterdata.address
(
	id varchar(36) not null
		constraint pk_address
			primary key,

	street varchar(255) NULL,
	city varchar(255) NULL,
	version bigint default 0
);


insert into masterdata.address (id, street, city) values ('999020de-a7db-49eb-8849-6dcbcab088e4', 'Evergreen Terrace 1', 'Springfield');
insert into masterdata.address (id, street, city) values ('b6df6f5b-62b4-45d3-a3c8-d42ec5483220', 'Everblue Terrace 1', 'Springfield');
insert into masterdata.address (id, street, city) values ('a443ecec-d90f-4f02-9b31-dac34150e278', 'Monty Mansion', 'Springfield');

