CREATE TABLE ID_NAME(id SERIAL PRIMARY KEY, name VARCHAR(32) NOT NULL, CONSTRAINT UK_idName_name UNIQUE (name));
