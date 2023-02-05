-- separate schema
CREATE SCHEMA IF NOT EXISTS masterdata;

-- catalog tables
CREATE TABLE masterdata.person (
   	id varchar(36) not null
		constraint pk_person
			primary key,

    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

insert into masterdata.person (id, first_name, last_name) values ('84b27449-3ac5-4e3f-81b7-345d25c56210', 'Homer', 'Simpson');
insert into masterdata.person (id, first_name, last_name) values ('5d9b79e0-c3da-4d7c-a411-6eb2ea6207e6', 'Bart', 'Simpson');
insert into masterdata.person (id, first_name, last_name) values ('145482e6-b174-4a83-b9af-60856dcacb1a', 'Monty', 'Burns');


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

