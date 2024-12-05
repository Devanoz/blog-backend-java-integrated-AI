ALTER TABLE post ADD COLUMN category_id INT, ADD CONSTRAINT foreign key (category_id) REFERENCES category(id)
