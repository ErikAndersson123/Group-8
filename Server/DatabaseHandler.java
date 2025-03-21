package Server;

import java.sql.*;
import java.util.Properties;
import java.util.LinkedList;

public class DatabaseHandler {
    
    private Connection conn;

    static final String DBNAME = "";
    static final String DATABASE = "jdbc:postgresql://localhost/" + DBNAME;
    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";
    
    public DatabaseHandler() {
        try {
            this.conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage(), e);
        }
    }

    public DatabaseHandler(String db, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        conn = DriverManager.getConnection(db, props);
    }
    
    public String registerUser(User user) {
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
            return "{\"success\":true}";
        } catch (SQLException e) {
            return "{\"success\":false, \"error\":\"" + getError(e) + "\"}";
        }
    }
    
    public String unregisterUser(User user) {
        String sql = "DELETE FROM Users WHERE userID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return "{\"success\":false, \"error\":\"User does not exist\"}";
            }
            return "{\"success\":true}";
        } catch (SQLException e) {
            return "{\"success\":false, \"error\":\"" + getError(e) + "\"}";
        }
    }

    public String registerChatroom(Chatroom chatroom) {
        String sql = "INSERT INTO Chatrooms (roomID, name) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chatroom.getRoomID());
            stmt.setString(2, chatroom.getName());
            stmt.executeUpdate();
            return "{\"success\":true}";
        } catch (SQLException e) {
            return "{\"success\":false, \"error\":\"" + getError(e) + "\"}";
        }
    }

    public String unregisterChatroom(Chatroom chatroom) {
        String sql = "DELETE FROM Chatrooms WHERE roomID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chatroom.getRoomID());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return "{\"success\":false, \"error\":\"Chatroom does not exist\"}";
            }
            return "{\"success\":true}";
        } catch (SQLException e) {
            return "{\"success\":false, \"error\":\"" + getError(e) + "\"}";
        }
    }

    public String registerChatroomUser(User user, Chatroom chatroom) {
        String sql = "INSERT INTO ChatroomUsers (userID, roomID) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, chatroom.getRoomID());
            stmt.executeUpdate();
            return "{\"success\":true}";
        } catch (SQLException e) {
            return "{\"success\":false, \"error\":\"" + getError(e) + "\"}";
        }
    }
    
    public String unregisterChatroomUser(User user, Chatroom chatroom) {
        String sql = "DELETE FROM ChatroomUsers WHERE userID = ? AND roomID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getUserID());
            stmt.setInt(2, chatroom.getRoomID());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return "{\"success\":false, \"error\":\"Chatroom or User does not exist\"}";
            }
            return "{\"success\":true}";
        } catch (SQLException e) {
            return "{\"success\":false, \"error\":\"" + getError(e) + "\"}";
        }        
    }   

    public String addMessage(Message message) {
        String sql = "INSERT INTO Messages (messageID, senderID, roomID, timestamp, text, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, message.getMessageID());
            stmt.setInt(2, message.getSenderID());
            stmt.setInt(3, message.getRoomID());           
            stmt.setString(4, String.valueOf(message.getTimestamp()));
            stmt.setString(5, message.getText());
            stmt.setString(6, message.getImage());
            stmt.executeUpdate();
            return "{\"success\":true}";
        } catch (SQLException e) {
            return "{\"success\":false, \"error\":\"" + getError(e) + "\"}";
        }
    }

    public String removeMessage(Message message) {
        String sql = "DELETE FROM Messages WHERE messageID = ? AND roomID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, message.getMessageID());
            stmt.setInt(2, message.getRoomID());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return "{\"success\":false, \"error\":\"Message does not exist\"}";
            }
            return "{\"success\":true}";
        } catch (SQLException e) {
            return "{\"success\":false, \"error\":\"" + getError(e) + "\"}";
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public int getUserID(User user) {
        String sql = "SELECT userID FROM Users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("userID");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            return -1;
        }
    }
    
    public int getRoomID(Chatroom chatroom) {
        String sql = "SELECT roomID FROM Chatrooms WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chatroom.getName());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("roomID");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            return -1;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


    public int nextAvailableRoomID() {
        LinkedList<Integer> roomIDs = new LinkedList<>();
        String sql = "SELECT roomID FROM Chatrooms";
    
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs= stmt.executeQuery()) {
                while (rs.next()) {
                    int roomID = rs.getInt("roomID");
                    roomIDs.add(roomID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int nextRoomID = 1;
        int index = 0;
        
        while (index < roomIDs.size() && roomIDs.get(index) == nextRoomID) {
            nextRoomID++;
            index++;
        }
    
        return nextRoomID;
    }
    
    public int nextAvailableMessageID(Chatroom chatroom) {
        LinkedList<Integer> messageIDs = new LinkedList<>();
        String sql = "SELECT messageID FROM Messages WHERE roomID = ?";
    
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, chatroom.getRoomID());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int messageID = rs.getInt("messageID");
                    messageIDs.add(messageID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        int nextMessageID = 1;
        int index = 0;
        
        while (index < messageIDs.size() && messageIDs.get(index) == nextMessageID) {
            nextMessageID++;
            index++;
        }
        
        return nextMessageID;
    }

    public String getImagePath(Message msg) {
        String sql = "SELECT image FROM messages WHERE messageID = ? AND roomID = ?";
        String imagePath = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, msg.getMessageID());
            pstmt.setInt(2, msg.getRoomID());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                imagePath = rs.getString("image");
                //System.out.println("Retrieved Image Path: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<>();
        String sql = "SELECT userID, username, password FROM Users";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userID = rs.getInt("userID");
                String username = rs.getString("username");
                String password = rs.getString("password");
                
                User user = new User(userID, username, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    public LinkedList<Chatroom> getAllChatrooms() {
        LinkedList<Chatroom> chatrooms = new LinkedList<>();
        String sql = "SELECT roomID, name FROM Chatrooms";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int roomID = rs.getInt("roomID");
                String name = rs.getString("name");

                Chatroom chatroom = new Chatroom(roomID, name);
                chatrooms.add(chatroom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatrooms;
    }
    
    public LinkedList<Message> getAllMessages(Chatroom chatroom) {
        LinkedList<Message> messages = new LinkedList<>();
        String sql = "SELECT messageID, senderID, roomID, timestamp, text, image FROM Messages WHERE roomID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, chatroom.getRoomID());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int messageID = rs.getInt("messageID");
                    int senderID = rs.getInt("senderID");
                    int roomID = rs.getInt("roomID");
                    Timestamp timestamp = rs.getTimestamp("timestamp");
                    String text = rs.getString("text");
                    String image = rs.getString("image");

                    Message message = new Message(messageID, senderID, roomID, timestamp, text, image);
                    messages.add(message);
                }
            }   
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
    
    public LinkedList<User> getAllChatroomUsers(Chatroom chatroom) {
        LinkedList<User> users = new LinkedList<>();
        String sql = "SELECT userID, username, password FROM ChatroomUsers LEFT JOIN Users USING (userID) WHERE roomID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, chatroom.getRoomID()); // Set parameter correctly before executing query

            try (ResultSet rs = stmt.executeQuery()) { // Execute query in a separate try block
                while (rs.next()) {
                    int userID = rs.getInt("userID");
                    String username = rs.getString("username");
                    String password = rs.getString("password");

                    User user = new User(userID, username, password);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    




    
    

    
    
    
    
    
    
    
    

    
    public static String getError(SQLException e) {
        String message = e.getMessage();
        int ix = message.indexOf('\n');
        if (ix > 0) message = message.substring(0, ix);
        message = message.replace("\"", "\\\"");
        return message;
    }
}