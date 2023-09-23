-- catalog tables
CREATE TABLE masterdata.person (
   	id varchar(36) not null
		constraint pk_person
			primary key,

    first_name VARCHAR(20),
    last_name VARCHAR(20),

    version bigint default 0
);