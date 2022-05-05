CREATE TABLE USERS (
	user_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	name TEXT,
	whatsapp TEXT,
	email TEXT,
	age INTEGER NOT NULL,
	about_me TEXT,
	password TEXT,
	friends TEXT,
	profilePic TEXT
);

INSERT INTO USERS
	VALUES (1, 'Damien Gens', '+33123456789', '100478208@alumnos.uc3m.es', 21, 'Hi I am French and Madrid is great', 'passworddamien','','');

INSERT INTO USERS
	VALUES (2, 'Haley Fogelson', '+1987643210', '100478209@alumnos.uc3m.es', 21, 'Hi I am American and Madrid is great', 'passwordhaley','','');

INSERT INTO USERS
	VALUES (3, 'Paul Bridier', '+33712345678', '100478207@alumnos.uc3m.es', 22, 'Hi I am French too and Madrid is great', 'passwordpaul','','');

CREATE TABLE EVENTS (
	event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	name TEXT,
	description TEXT,
	date INTEGER NOT NULL,
	creator INTEGER NOT NULL,
	eventPic TEXT,
	location TEXT,
	FOREIGN KEY (creator)
	REFERENCES USERS(user_id)
	ON DELETE CASCADE
);

INSERT INTO EVENTS
	VALUES(1, 'Event1', 'cool event to drink', 2000000000, 3, 'https://media.istockphoto.com/photos/group-of-friends-enjoying-drinks-at-bar-picture-id174951485?k=20&m=174951485&s=612x612&w=0&h=VTE7OgRXtA2D13g_GjZBNzckFKpBF97BjzKmFwHTLV8=', '');

INSERT INTO EVENTS
	VALUES(2, 'Event2', 'another event', 2000000000, 1, 'https://images.pexels.com/photos/8455350/pexels-photo-8455350.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500', '');

INSERT INTO EVENTS
	VALUES(3, 'Event3', 'best event', 2000000000, 2, 'https://barozziveiga.com/media/pages/projects/museum-of-fine-arts/4029461183-1623917242/12_19-10-mcba-ebv_simon-menges_06_hires.jpg', '');

CREATE TABLE ASSOCIATIONS (
	user_id_assoc INTEGER NOT NULL,
	event_id_assoc INTEGER NOT NULL,
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
