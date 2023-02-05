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