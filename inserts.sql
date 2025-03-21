INSERT INTO Users VALUES('NewUser', '123');
INSERT INTO Users VALUES('Erik','123');
INSERT INTO Users VALUES('William','123');
INSERT INTO Users VALUES('Alexander','123');
INSERT INTO Users VALUES('Anton','123');
INSERT INTO Users VALUES('Gabriel','123');

--INSERT INTO Chatrooms VALUES(0, 'Chat0');

INSERT INTO Chatrooms VALUES(1, 'Chat1');
INSERT INTO Chatrooms VALUES(2, 'Chat2');
INSERT INTO Chatrooms VALUES(3, 'Chat3');
INSERT INTO Chatrooms VALUES(4, 'Chat4');
INSERT INTO Chatrooms VALUES(5, 'Chat5');

INSERT INTO ChatroomUsers VALUES(1, 1);
INSERT INTO ChatroomUsers VALUES(1, 2);
INSERT INTO ChatroomUsers VALUES(1, 3);
INSERT INTO ChatroomUsers VALUES(1, 4);
INSERT INTO ChatroomUsers VALUES(1, 5);
 
INSERT INTO ChatroomUsers VALUES(2, 1);
INSERT INTO ChatroomUsers VALUES(2, 2);
INSERT INTO ChatroomUsers VALUES(2, 3);
INSERT INTO ChatroomUsers VALUES(2, 4);
INSERT INTO ChatroomUsers VALUES(2, 5);

INSERT INTO ChatroomUsers VALUES(3, 1);
INSERT INTO ChatroomUsers VALUES(3, 2);
INSERT INTO ChatroomUsers VALUES(3, 3);
INSERT INTO ChatroomUsers VALUES(3, 4);
INSERT INTO ChatroomUsers VALUES(3, 5);

INSERT INTO ChatroomUsers VALUES(4, 1);
INSERT INTO ChatroomUsers VALUES(4, 2);
INSERT INTO ChatroomUsers VALUES(4, 3);
INSERT INTO ChatroomUsers VALUES(4, 4);
INSERT INTO ChatroomUsers VALUES(4, 5);

INSERT INTO ChatroomUsers VALUES(5, 1);
INSERT INTO ChatroomUsers VALUES(5, 2);
INSERT INTO ChatroomUsers VALUES(5, 3);
INSERT INTO ChatroomUsers VALUES(5, 4);
INSERT INTO ChatroomUsers VALUES(5, 5);

INSERT INTO Messages (messageID, senderID, roomID, timestamp, text, image) VALUES
--(1, 1, 1, '2025-03-02 10:00:00', 'Hello everyone!', 'C:\\Users\\Per\\Desktop\\test1\\1.jpg'),
(2, 2, 1, '2025-03-02 10:01:00', 'Hi there!', NULL),
(3, 3, 1, '2025-03-02 10:02:00', 'Good morning!', NULL),
(4, 1, 2, '2025-03-02 10:03:00', 'Welcome to room 2!', NULL),
(5, 4, 2, '2025-03-02 10:04:00', 'Thanks!', NULL),
(6, 2, 1, '2025-03-02 10:05:00', 'How is everyone doing?', NULL),
(7, 3, 3, '2025-03-02 10:06:00', 'This is a message in room 3.', NULL),
(8, 1, 3, '2025-03-02 10:07:00', 'Nice to meet you all!', NULL),
(9, 4, 3, '2025-03-02 10:08:00', 'Hey!', NULL),
(10, 5, 1, '2025-03-02 10:09:00', 'Random text.', NULL),
(11, 6, 2, '2025-03-02 10:10:00', 'Good afternoon.', NULL),
(12, 2, 3, '2025-03-02 10:11:00', 'Hope you have a great day!', NULL),
(13, 1, 1, '2025-03-02 10:12:00', 'Meeting at 3 PM.', NULL),
(14, 3, 2, '2025-03-02 10:13:00', 'See you later.', NULL),
--(15, 5, 3, '2025-03-02 10:14:00', 'Sharing an image.', 'C:\\Users\\Per\\Desktop\\test1\\1.jpg'),
(16, 7, 1, '2025-03-02 10:15:00', 'Welcome to the chat.', NULL),
(17, 8, 2, '2025-03-02 10:16:00', 'Glad to be here!', NULL),
(18, 9, 3, '2025-03-02 10:17:00', 'Excited for the weekend.', NULL),
(19, 10, 1, '2025-03-02 10:18:00', 'Any plans for today?', NULL),
(20, 1, 2, '2025-03-02 10:19:00', 'Just chilling.', NULL),
(21, 2, 3, '2025-03-02 10:20:00', 'Let’s meet at noon.', NULL),
(22, 3, 1, '2025-03-02 10:21:00', 'Working on something important.', NULL),
--(23, 4, 2, '2025-03-02 10:22:00', 'Sent an attachment.', 'C:\\Users\\Per\\Desktop\\test1\\1.jpg'),
(24, 5, 3, '2025-03-02 10:23:00', 'Waiting for your reply.', NULL),
(25, 6, 1, '2025-03-02 10:24:00', 'I’m on my way.', NULL),
--(26, 7, 2, '2025-03-02 10:25:00', 'Check this out.', 'C:\\Users\\Per\\Desktop\\test1\\1.jpg'),
(27, 8, 3, '2025-03-02 10:26:00', 'Did you finish the report?', NULL),
(28, 9, 1, '2025-03-02 10:27:00', 'See you soon.', NULL),
(29, 10, 2, '2025-03-02 10:28:00', 'Enjoy your meal.', NULL),
(30, 1, 3, '2025-03-02 10:29:00', 'Thanks for the info!', NULL),
(31, 2, 1, '2025-03-02 10:30:00', 'Sending another message.', NULL),
(32, 3, 2, '2025-03-02 10:31:00', 'I appreciate it.', NULL),
(33, 4, 3, '2025-03-02 10:32:00', 'See you tomorrow.', NULL),
(34, 5, 1, '2025-03-02 10:33:00', 'Good night!', NULL),
(35, 6, 2, '2025-03-02 10:34:00', 'I need help with something.', NULL),
(36, 7, 3, '2025-03-02 10:35:00', 'Where are you?', NULL),
(37, 8, 1, '2025-03-02 10:36:00', 'Let’s start the project.', NULL),
(38, 9, 2, '2025-03-02 10:37:00', 'Got it.', NULL),
(39, 10, 3, '2025-03-02 10:38:00', 'Confirming the details.', NULL),
(40, 1, 1, '2025-03-02 10:39:00', 'Have a great day!', NULL),
(41, 2, 2, '2025-03-02 10:40:00', 'Looking forward to it.', NULL),
(42, 3, 3, '2025-03-02 10:41:00', 'Interesting!', NULL),
(43, 4, 1, '2025-03-02 10:42:00', 'Need to check something.', NULL),
(44, 5, 2, '2025-03-02 10:43:00', 'I will let you know.', NULL),
(45, 6, 3, '2025-03-02 10:44:00', 'Thanks for your patience.', NULL),
(46, 7, 1, '2025-03-02 10:45:00', 'Great job!', NULL),
--(47, 8, 2, '2025-03-02 10:46:00', 'Sharing another image.', 'C:\\Users\\Per\\Desktop\\test1\\1.jpg'),
(48, 9, 3, '2025-03-02 10:47:00', 'Good news!', NULL),
(49, 10, 1, '2025-03-02 10:48:00', 'Stay safe.', NULL),
(50, 1, 2, '2025-03-02 10:49:00', 'Talk to you later.', NULL);
