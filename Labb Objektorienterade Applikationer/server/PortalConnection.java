package server;

import java.sql.*; // JDBC stuff.
import java.util.Properties;

public class PortalConnection {

    // Set this to e.g. "portal" if you have created a database named portal
    // Leave it blank to use the default database of your database user
    static final String DBNAME = "";
    // For connecting to the portal database on your local machine
    static final String DATABASE = "jdbc:postgresql://localhost/"+DBNAME;
    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";

    // This is the JDBC connection object you will be using in your methods.
    private Connection conn;
    
    /*
    public PortalConnection() throws SQLException, ClassNotFoundException {
        this(DATABASE, USERNAME, PASSWORD);  
    }
    */
   
    public PortalConnection() {
        try {
            this.conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage(), e);
        }
    }



    // Initializes the connection, no need to change anything here
    public PortalConnection(String db, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        conn = DriverManager.getConnection(db, props);
    }

    public String registerUser(User user){
    String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, user.getName());
        stmt.setString(2, user.getPassword());
        stmt.executeUpdate();
        return "{\"success\":true}";
      } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }     
    }

    public String unregisterUser(User user){
    String sql = "DELETE FROM Users WHERE username = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, user.getName());
        stmt.executeUpdate();
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            return "{\"success\":false, \"error\":\"User does not exist\"}";
        }        
        return "{\"success\":true}";
      } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }
    }
    
    public boolean authenticateUser(User user){
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            ResultSet rs = stmt.executeQuery();
        
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public String registerChatroom(Chatroom chatroom){
    String sql = "INSERT INTO Chatrooms (roomID) VALUES (?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, chatroom.getRoomID());
        stmt.executeUpdate();
        return "{\"success\":true}";
      } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }     
    }
    
    public String unregisterChatroom(Chatroom chatroom){
    String sql = "DELETE FROM Chatrooms WHERE roomID = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, chatroom.getRoomID());
        stmt.executeUpdate();
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            return "{\"success\":false, \"error\":\"Chatroom does not exist\"}";
        }        
        return "{\"success\":true}";
      } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }
    }
    
    public String addMessage(Message message){
    String sql = "INSERT INTO Messages (senderID, roomID, timestamp, text, image) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, message.getSenderID());
        stmt.setInt(2, message.getRoomID());
        stmt.setString(3, message.getTimestamp());
        stmt.setString(4, message.getText());
        stmt.setString(5, message.getImage());
        stmt.executeUpdate();
        return "{\"success\":true}";
      } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }     
    }

    public String removeMessage(Message message){
    String sql = "DELETE FROM Messages WHERE senderID = ? AND roomID = ? AND timestamp = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, message.getSenderID());
        stmt.setInt(2, message.getRoomID());
        stmt.setString(3, message.getTimestamp());
        stmt.executeUpdate();
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            return "{\"success\":false, \"error\":\"User does not exist\"}";
        }        
        return "{\"success\":true}";
      } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }
    }
    
    // This is a hack to turn an SQLException into a JSON string error message. No need to change.
    public static String getError(SQLException e){
       String message = e.getMessage();
       int ix = message.indexOf('\n');
       if (ix > 0) message = message.substring(0, ix);
       message = message.replace("\"","\\\"");
       return message;
    }
}