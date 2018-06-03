CREATE TABLE IF NOT EXISTS items (
	id varchar(15),
	name varchar(30) NOT NULL,
	description varchar(100),
	created bigint  NOT NULL,
	PRIMARY KEY (id));	

CREATE TABLE IF NOT EXISTS comments (
	id serial, 
	comment text,
	itemId varchar(15),
	PRIMARY KEY (id),
	FOREIGN KEY (itemId) REFERENCES items (id) ON UPDATE CASCADE ON DELETE CASCADE);

