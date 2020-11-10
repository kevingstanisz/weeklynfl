ALTER TABLE users DROP id;

ALTER TABLE users
ADD id serial not null primary key;