-- separate schema
CREATE SCHEMA IF NOT EXISTS catalogs;

-- catalog tables
CREATE TABLE catalogs.person_catalog  (
   	id varchar(36) not null
		constraint pk_people
			primary key,
    catalog_version VARCHAR(20),

    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

CREATE TABLE catalogs.toy_catalog (
   	id varchar(36) not null
		constraint pk_toys
			primary key,
    catalog_version VARCHAR(20),

    toy_name VARCHAR(20),
    price VARCHAR(20)
);

