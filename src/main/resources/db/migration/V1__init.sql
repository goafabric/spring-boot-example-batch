DROP TABLE IF EXISTS people;

CREATE TABLE people  (
    id varchar(36) not null
            constraint id_people,
                primary key,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);