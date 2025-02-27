-- Insert Users
INSERT INTO Users (username, password) VALUES 
('william', '5412'),
('alice', 'password123'),
('bob', 'securepass'),
('charlie', 'charliepass');

-- Insert Chatrooms
INSERT INTO Chatrooms (roomID, name) VALUES 
(1, 'General'),
(2, 'Tech'),
(3, 'Gaming');

-- Insert Messages
INSERT INTO Messages (messageID, senderID, roomID, timestamp, text) VALUES
(1, 2, 1, '2025-02-20 10:00:00', 'Hello, everyone!'),
(2, 3, 1, '2025-02-20 10:05:00', 'Hi Alice!'),
(3, 4, 1, '2025-02-20 10:10:00', 'Good morning!'),
(4, 2, 1, '2025-02-20 10:15:00', 'How is your day going?'),
(5, 3, 1, '2025-02-20 10:20:00', 'Not bad, just working.'),
(6, 4, 1, '2025-02-20 10:25:00', 'I have a meeting soon.'),
(7, 2, 2, '2025-02-20 11:00:00', 'Anyone here?'),
(8, 3, 3, '2025-02-20 12:00:00', 'Let’s play some games!'),
(9, 2, 1, '2025-02-21 08:30:00', 'Morning, everyone!'),
(10, 3, 1, '2025-02-21 08:35:00', 'Hey! How are you?'),
(11, 4, 2, '2025-02-21 09:00:00', 'Tech Talk is the best!'),
(12, 2, 3, '2025-02-21 09:15:00', 'New game update is out.'),
(13, 3, 1, '2025-02-21 09:30:00', 'I love this chat.'),
(14, 4, 1, '2025-02-21 10:00:00', 'Weekend plans?'),
(15, 2, 2, '2025-02-21 10:15:00', 'Coding challenge today.'),
(16, 3, 3, '2025-02-21 11:00:00', 'Need a new gaming mouse.'),
(17, 4, 1, '2025-02-21 11:30:00', 'Coffee break!'),
(18, 2, 2, '2025-02-21 12:00:00', 'Anyone free for lunch?'),
(19, 3, 3, '2025-02-21 12:30:00', 'Multiplayer tonight?'),
(20, 4, 1, '2025-02-21 13:00:00', 'Back to work.'),
(21, 2, 2, '2025-02-21 13:30:00', 'Checking messages.'),
(22, 3, 3, '2025-02-21 14:00:00', 'Game night confirmed!'),
(23, 4, 1, '2025-02-21 14:30:00', 'What’s up?'),
(24, 2, 2, '2025-02-21 15:00:00', 'Still working.'),
(25, 3, 3, '2025-02-21 15:30:00', 'Any movie recommendations?'),
(26, 4, 1, '2025-02-21 16:00:00', 'Time for a walk.'),
(27, 2, 2, '2025-02-21 16:30:00', 'Learning SQL today.'),
(28, 3, 3, '2025-02-21 17:00:00', 'Battle Royale time!'),
(29, 4, 1, '2025-02-21 17:30:00', 'End of the workday.'),
(30, 2, 2, '2025-02-21 18:00:00', 'Dinner time.'),
(31, 3, 3, '2025-02-21 18:30:00', 'Streaming now!'),
(32, 4, 1, '2025-02-21 19:00:00', 'Heading home.'),
(33, 2, 2, '2025-02-21 19:30:00', 'Late-night coding.'),
(34, 3, 3, '2025-02-21 20:00:00', 'New game release!'),
(35, 4, 1, '2025-02-21 20:30:00', 'Chilling now.'),
(36, 2, 2, '2025-02-21 21:00:00', 'Almost bedtime.'),
(37, 3, 3, '2025-02-21 21:30:00', 'Final match of the night.'),
(38, 4, 1, '2025-02-21 22:00:00', 'Goodnight, chat!'),
(39, 2, 2, '2025-02-21 22:30:00', 'See you all tomorrow!'),
(40, 3, 3, '2025-02-21 23:00:00', 'Logging off.'),
(41, 4, 1, '2025-02-21 23:30:00', 'Last message of the day!'),
(42, 2, 2, '2025-02-22 08:00:00', 'Good morning!'),
(43, 3, 3, '2025-02-22 08:30:00', 'Coffee first!'),
(44, 4, 1, '2025-02-22 09:00:00', 'Back to work.'),
(45, 2, 2, '2025-02-22 09:30:00', 'Working on a project.'),
(46, 3, 3, '2025-02-22 10:00:00', 'Game recommendations?'),
(47, 4, 1, '2025-02-22 10:30:00', 'Break time.'),
(48, 2, 2, '2025-02-22 11:00:00', 'Anyone online?'),
(49, 3, 3, '2025-02-22 11:30:00', 'Weekend plans?'),
(50, 4, 1, '2025-02-22 12:00:00', 'Lunch break!');


-- Insert ChatroomUsers
INSERT INTO ChatroomUsers (userID, roomID) VALUES 
(2, 1), -- Alice in General Chat
(3, 1), -- Bob in General Chat
(4, 1), -- Charlie in General Chat
(2, 2), -- Alice in Tech Talk
(3, 3); -- Bob in Gaming




