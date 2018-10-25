create type gender as enum ('MALE', 'FEMALE');
CREATE TABLE people
(
    id serial primary key,
    name varchar(40) NOT NULL,
    surname varchar(40)  NOT NULL,
    age int NOT NULL,
    tax_number bigint NOT NULL,
    region varchar(40) NOT NULL,
    gender gender
)