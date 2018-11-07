# Ticket and Payment System

### How to run

#### Create / Reset Database

 * `cd server/db/`
 * `sqlite3 db.sqlite3 < db.sql`

#### Run Local Server

 * `cd server/`
 * `npm install`
 * `npm start`

### RESTful Web Services

| Verb  | Endpoint | Req. Body | Res. Body | Description |
| ----- | -------- | --------- | --------- | ----------- |
| POST | /login | { username, password } | { } | Check if credentials match. |
| GET   | /users/:id | { } | { name, username, nif } | Get user by id. |
| POST | /users | { name, username, password, nif, keyN, keyE } | { id } | Create a new user. |
| PUT | /users/:id | { name, password, nif } | { } | Update user by id. |
| GET   | /users/:id/creditcard | { } | { id, type, number, validity } | Get user's credit card. |
| POST | /users/:id/creditcard | { type, number, validity } | { id } | Create new user's credit card. |
| PUT | /users/:id/creditcard | { type, number, validity } | { } | Update user's credit card. |
| GET | /shows?page=\<PAGE\>&limit=\<LIMIT\> | { } | { id, name, description, date } | Get the next airing shows.
