-- This file is used to run your whole database setup. It will:
-- * Delete the whole database (!)
-- * Run files that create tables, insert data, create views, and later create triggers.
-- * Do any additional stuff you need, like testing views or triggers
-- You can and should modify this file as you progress in the assignment.

-- Command to run this file (modify this if needed):
-- psql -f runsetup.sql

/*

-- This script deletes everything in your database
-- this is the only part of the script you do not need to understand
\set QUIET true
SET client_min_messages TO WARNING; -- Less talk please.
-- This script deletes everything in your database
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO CURRENT_USER;
-- This line makes psql stop on the first error it encounters
-- You may want to remove this when running tests that are intended to fail
\set ON_ERROR_STOP ON
SET client_min_messages TO NOTICE; -- More talk
\set QUIET false


-- \ir is for include relative, it will run files in the same directory as this file
-- Note that these are not SQL statements but rather Postgres commands (no terminating semicolon). 
\ir tables.sql
\ir inserts.sql
--\ir triggers.sql

--triggers.sql
-- This line makes psql continue even if there are errors (useful for testing triggers)
-- \set ON_ERROR_STOP OFF
-- \ir tests.sql

*/

SELECT userID, username, password FROM Users;

SELECT * FROM Chatrooms;

SELECT * FROM Messages;

SELECT * FROM ChatroomUsers;