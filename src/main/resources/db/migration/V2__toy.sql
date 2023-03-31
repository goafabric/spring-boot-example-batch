-- separate schema
CREATE SCHEMA IF NOT EXISTS masterdata;

-- catalog tables

CREATE TABLE masterdata.toy_catalog (
   	id varchar(36) not null
		constraint pk_toys
			primary key,
    toy_name VARCHAR(20),
    price VARCHAR(20)
);

