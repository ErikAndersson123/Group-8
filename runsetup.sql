

-- This file is used to run your whole database setup. It will:
-- * Delete the whole database (!)
-- * Run files that create tables, insert data, create views, and later create triggers.
-- * Do any additional stuff you need, like testing views or triggers
-- You can and should modify this file as you progress in the assignment.

-- Command to run this file (modify this if needed):
-- psql -f runsetup.sql


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



SELECT userID, username, password FROM Users;

SELECT * FROM Chatrooms;

SELECT * FROM Messages;

SELECT * FROM ChatroomUsers;






/*


create database chatapp
    with owner postgres;

\c chatapp


This script deletes everything in your database
\set QUIET true
SET client_min_messages TO WARNING; -- Less talk please.
DROP SCHEMA chat CASCADE;
CREATE SCHEMA chat AUTHORIZATION postgres;

SET client_min_messages TO NOTICE; -- More talk
\set QUIET false


--command to run sql file if you have postgres added to path. postgres:postgres is your postgres:password
-- psql -f "ChatAppDB.sql" postgresql://postgres:postgres@127.0.0.1

SET client_encoding = 'UTF8';

create table chat.users
(
    id           serial
        constraint users_pk
            primary key,
    username     varchar(20),
    password     text,
    profile_picture text,
    time_created timestamp default CURRENT_TIMESTAMP
);

alter table chat.users
    owner to postgres;

create table chat.chat_rooms
(
    id           serial
        constraint chat_rooms_pk
            primary key,
    name         varchar(20),
    max_members  integer   default 2,
    is_private   boolean   default true,
    time_created timestamp default CURRENT_TIMESTAMP,
    owner        integer not null
        constraint chat_rooms_users_id_fk
            references chat.users
);

alter table chat.chat_rooms
    owner to postgres;

create index chat_rooms_name_index
    on chat.chat_rooms (name);

create index users_username_index
    on chat.users (username);

create table chat.chat_room_messages
(
    id        serial
        constraint chat_room_messages_pk
            primary key,
    chat_room integer
        constraint chat_room_messages_chat_rooms_id_fk
            references chat.chat_rooms
            on delete cascade,
    sender    integer
        constraint chat_room_messages_users_id_fk
            references chat.users,
    message   text,
    is_image boolean default false,
    time_sent timestamp default CURRENT_TIMESTAMP,
    time_edited timestamp default CURRENT_TIMESTAMP
);

alter table chat.chat_room_messages
    owner to postgres;


create table chat.chat_room_message_reactions
(
    id           serial
        constraint chat_room_message_reactions_pk
            primary key,
    reacted      integer
        constraint chat_room_message_reactions_users_id_fk
            references chat.users,
    message      integer
        constraint chat_room_message_reactions_chat_room_messages_id_fk
            references chat.chat_room_messages
            on delete cascade,
    reaction     text,
    time_reacted timestamp default CURRENT_TIMESTAMP,
    constraint chat_room_message_reactions_pk_2
        unique (reacted, message, reaction)
);

alter table chat.chat_room_message_reactions
    owner to postgres;

create table chat.chat_room_members
(
    member    integer not null
        constraint chat_room_members_users_id_fk
            references chat.users,
    chat_room integer not null
        constraint chat_room_members_chat_rooms_id_fk
            references chat.chat_rooms
            on delete cascade,
    last_active timestamp default CURRENT_TIMESTAMP,
    constraint chat_room_members_pk
        primary key (chat_room, member)
);

alter table chat.chat_room_members
    owner to postgres;

 
create table chat.chat_room_ban
(
    user_id    integer not null
        constraint chat_room_ban_users_id_fk
            references chat.users,
    chat_room integer not null
        constraint chat_room_ban_chat_rooms_id_fk
            references chat.chat_rooms
            on delete cascade,
        primary key (chat_room, user_id)
);

alter table chat.chat_room_ban
    owner to postgres;


-- alter tables ****
alter table chat.chat_rooms
    add blob text;


-- TRIGGERS ****

CREATE OR REPLACE FUNCTION update_message_timestamp() 
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.message IS DISTINCT FROM OLD.message THEN
    NEW.time_edited = NOW();
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_message_timestamp_trigger
BEFORE UPDATE ON chat.chat_room_messages
FOR EACH ROW
EXECUTE FUNCTION update_message_timestamp();
*/