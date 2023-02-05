-- separate schema
CREATE SCHEMA IF NOT EXISTS masterdata;

-- catalog tables

CREATE TABLE masterdata.toy_catalog (
   	id varchar(36) not null
		constraint pk_toys
			primary key,
    catalog_version VARCHAR(20),

    toy_name VARCHAR(20),
    price VARCHAR(20)
);

