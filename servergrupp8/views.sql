CREATE VIEW ViewRoomMessages AS
SELECT 
    M.roomID, 
    M.senderID, 
    U.username, 
    M.timestamp, 
    M.text, 
    M.image
FROM Messages M
JOIN Users U ON M.senderID = U.username;



CREATE VIEW ViewChatroomUsers AS
SELECT 
    C.roomID, 
    U.username 
FROM ChatroomUsers C
JOIN Users U ON C.userID = U.username;



CREATE VIEW ViewLatestMessages AS
SELECT M1.roomID, M1.senderID, M1.timestamp, M1.text, M1.image
FROM Messages M1
WHERE M1.timestamp = (
    SELECT MAX(M2.timestamp)
    FROM Messages M2
    WHERE M1.roomID = M2.roomID
);



CREATE VIEW ViewUserMessages AS
SELECT 
    M.senderID, 
    M.roomID, 
    C.roomID AS chatroom, 
    M.timestamp, 
    M.text, 
    M.image
FROM Messages M
JOIN Chatrooms C ON M.roomID = C.roomID;



CREATE VIEW ViewMessageCount AS
SELECT roomID, COUNT(*) AS message_count
FROM Messages
GROUP BY roomID;
