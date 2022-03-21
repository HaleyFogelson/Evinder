CREATE TABLE USERS (
	user_id INTEGER PRIMARY KEY AUTOINCREMENT,
	name VARCHAR(30),
	whatsapp VARCHAR(30),
	email VARCHAR(50),
	age INTEGER,
	about_me VARCHAR(100),
	password VARCHAR(260)
);

CREATE TABLE EVENTS (
	event_id INTEGER PRIMARY KEY AUTOINCREMENT,
	name VARCHAR(30),
	description VARCHAR(100),
	date DATE,
	creator INTEGER,
	FOREIGN KEY (creator)
	REFERENCES USERS(user_id)
	ON DELETE CASCADE	
);

CREATE TABLE ASSOCIATIONS (
	user_id_assoc INTEGER,
	event_id_assoc INTEGER,
	PRIMARY KEY(event_id_assoc, user_id_assoc),
	FOREIGN KEY (user_id_assoc)
	REFERENCES USERS(user_id)
	ON DELETE CASCADE
	FOREIGN KEY (event_id_assoc)
	REFERENCES EVENTS(event_id)
	ON DELETE CASCADE
);