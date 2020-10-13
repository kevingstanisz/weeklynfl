CREATE TABLE person (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO person (id, name)  VALUES (uuid_generate_v4(), 'kev');