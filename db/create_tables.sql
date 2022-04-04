CREATE TABLE USERS (
	user_id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT,
	whatsapp TEXT,
	email TEXT,
	age INTEGER,
	about_me TEXT,
	password TEXT
);

INSERT INTO USERS
	VALUES (1, 'Damien Gens', '+33123456789', '100478208@alumnos.uc3m.es', 21, 'Hi I am French and Madrid is great', 'passworddamien');

INSERT INTO USERS
	VALUES (2, 'Haley Fogelson', '+1987643210', '100478209@alumnos.uc3m.es', 21, 'Hi I am American and Madrid is great', 'passwordhaley');

INSERT INTO USERS
	VALUES (3, 'Paul Bridier', '+33712345678', '100478207@alumnos.uc3m.es', 22, 'Hi I am French too and Madrid is great', 'passwordpaul');

CREATE TABLE EVENTS (
	event_id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT,
	description TEXT,
	date INTEGER,
	creator INTEGER,
	FOREIGN KEY (creator)
	REFERENCES USERS(user_id)
	ON DELETE CASCADE	
);

INSERT INTO EVENTS
	VALUES(1, 'Event1', 'cool event to drink', 2000000000, 3);

INSERT INTO EVENTS
	VALUES(2, 'Event2', 'another event', 2000000000, 1);

INSERT INTO EVENTS
	VALUES(3, 'Event3', 'best event', 2000000000, 2);

CREATE TABLE ASSOCIATIONS (
	user_id_assoc INTEGER,
	event_id_assoc INTEGER,
	PRIMARY KEY(user_id_assoc, event_id_assoc),
	FOREIGN KEY (user_id_assoc)
	REFERENCES USERS(user_id)
	ON DELETE CASCADE
	FOREIGN KEY (event_id_assoc)
	REFERENCES EVENTS(event_id)
	ON DELETE CASCADE
);

INSERT INTO ASSOCIATIONS
	VALUES(2, 1);

INSERT INTO ASSOCIATIONS
	VALUES(1, 3);
