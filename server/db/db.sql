
.mode columns
.headers on
.nullvalue NULL

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS CreditCards;
DROP TABLE IF EXISTS Shows;
DROP TABLE IF EXISTS Tickets;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Vouchers;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Promotions;
DROP TABLE IF EXISTS ProductOrders;

PRAGMA FOREIGN_KEYS = ON;

CREATE TABLE Users (
	id TEXT PRIMARY KEY,
	name TEXT NOT NULL,
	username TEXT NOT NULL UNIQUE,
	password TEXT NOT NULL,
	nif TEXT NOT NULL UNIQUE,
	keyN TEXT NOT NULL,
	keyE TEXT NOT NULL,
	UNIQUE(keyN, keyE)
);

CREATE TABLE CreditCards (
	id INTEGER PRIMARY KEY,
	type TEXT NOT NULL,
	number TEXT NOT NULL UNIQUE,
	validity DATE NOT NULL,
	userId INTEGER UNIQUE REFERENCES Users(id)
);

CREATE TABLE Shows (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	description TEXT,
	date DATETIME NOT NULL,
	price DOUBLE NOT NULL
);

CREATE TABLE Tickets (
	id TEXT PRIMARY KEY,
	seatNumber INTEGER NOT NULL,
	showId INTEGER NOT NULL REFERENCES Shows(id),
	userId INTEGER NOT NULL REFERENCES Users(id)
);

CREATE TABLE Orders (
	id INTEGER PRIMARY KEY,
	date DATETIME NOT NULL,
	userId INTEGER NOT NULL REFERENCES Users(id)
);

CREATE TABLE Vouchers (
	id INTEGER PRIMARY KEY,
	available BOOLEAN NOT NULL DEFAULT TRUE,
	userId INTEGER NOT NULL REFERENCES Users(id),
	orderId INTEGER REFERENCES Orders(id)
);

CREATE TABLE Products (
	id INTEGER PRIMARY KEY,
	name TEXT NOT NULL,
	price DOUBLE NOT NULL
);

CREATE TABLE Promotions (
	voucherId INTEGER NOT NULL REFERENCES Vouchers(id),
	productId INTEGER NOT NULL REFERENCES Products(id),
	discount DOUBLE NOT NULL,
	PRIMARY KEY (voucherId, productId)
);

CREATE TABLE ProductOrders (
	productId NOT NULL REFERENCES Products(id),
	orderId REFERENCES Orders(id),
	quantity INTEGER NOT NULL,
	PRIMARY KEY (productId, orderId)
);


/* --- TEST DATA --- */
/* --- USERS --- */

INSERT INTO Users (id, name, username, password, nif, keyN, keyE) VALUES ("one", "tiago", "tirafesi", "$2b$10$4hhfZMgRaZ9JerjwAuNSt.Y4EgsELabjubyEnSB0/rfK5ObSJAGG.", "987654321", "c5b6e9093307afbe53fd29d9b4944d92a2bdd5b94bbc6247590dc8f4a768b3fa36874390f5da36ea2823459052c097d0fd75706880e4641a1e5a8f89e9580ce9", "10001");
INSERT INTO Users (id, name, username, password, nif, keyN, keyE) VALUES ("two", "claudia", "arwen7stars", "$2b$10$4hhfZMgRaZ9JerjwAuNSt.Y4EgsELabjubyEnSB0/rfK5ObSJAGG.", "876543210", "n2", "e2");
INSERT INTO CreditCards (type, number, validity, userId) VALUES ("Master Card", "123456789", "2020-03-21", "one");
INSERT INTO CreditCards (type, number, validity, userId) VALUES ("Master Card", "012345678", "2020-03-21", "two");

/* --- SHOWS --- */

INSERT INTO Shows (name, description, date, price) VALUES ("Dead Combo", "A deadly combo is gonna happen", "2019-10-25", 9.99);
INSERT INTO Shows (name, description, date, price) VALUES ("Jojo Mayer & Nerve", "Is that a jojo's reference!?", "2019-10-26", 4.99);
INSERT INTO Shows (name, description, date, price) VALUES ("Anna von Hausswolff", "Reminds me of VanHelsing, the vampire slayer", "2019-11-04", 13.50);
INSERT INTO Shows (name, description, date, price) VALUES ("Júlio Resende", "Also know as Julio Cesar of Rome", "2019-11-13", 27.50);
INSERT INTO Shows (name, description, date, price) VALUES ("Festival Termómetro", "It's gonna be hoooot!", "2019-11-16", 19.99);