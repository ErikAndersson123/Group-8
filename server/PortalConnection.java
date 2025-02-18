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

    public PortalConnection() throws SQLException, ClassNotFoundException {
        this(DATABASE, USERNAME, PASSWORD);
    }
    // Initializes the connection, no need to change anything here
    public PortalConnection(String db, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        conn = DriverManager.getConnection(db, props);
    }


    // Register a user to our platform, returns a tiny JSON document (as a String)
    public String register(String username, String password){
      // Here's a bit of useful code, use it or delete it 
    	try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Users VALUES(?, ?");){
    		ps.setString(1, username);
    		ps.setString(2, password);
    		ps.executeUpdate();
    		return "{\"success\":true}";
       } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }     
    }
    
    // Unregister a user from our platform, returns a tiny JSON document (as a String)
    public String unregister(String username, String password){
    	try(PreparedStatement ps = conn.prepareStatement("DELETE FROM Users WHERE username=? AND password=?");){
    		ps.setString(1, username);
    		ps.setString(2, password);
    		int s = ps.executeUpdate();
    		return s > 0 ? "{\"success\":true, "+s+" rows affected" : "{\"success\":false, \"error\":\"0 rows affected\"}";
    	} catch (SQLException e) {
    	      return "{\"success\":false, \"error\":\""+getError(e)+"\"}";

    	}
    }
    public String updateUserInfo(String username, String password) {
    	try(PreparedStatement ps = conn.prepareStatement("UPDATE Users SET password=? WHERE username=?");){
    		ps.setString(1, username);
    		ps.setString(2, password);
    		int s = ps.executeUpdate();
    		return s > 0 ? "{\"success\":true, "+s+" rows affected" : "{\"success\":false, \"error\":\"0 rows affected\"}";
    	} catch (SQLException e) {
    	      return "{\"success\":false, \"error\":\""+getError(e)+"\"}";

    	}
    }
    // register a chatroom
    public String Createroom(int roomID){
        // Here's a bit of useful code, use it or delete it 
      	try (PreparedStatement ps = conn.prepareStatement("INSERT INTO chatroom VALUES(?)");){
      		ps.setInt(1, roomID);
      		ps.executeUpdate();
      		return "{\"success\":true}";
         } catch (SQLException e) {
            return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
        }     
      }
    // remove a chatroom
    public String removeRoom(int roomID){
        // Here's a bit of useful code, use it or delete it 
      	try (PreparedStatement ps = conn.prepareStatement("DELETE FROM chatroom WHERE roomID=?");){
      		ps.setInt(1, roomID);
      		ps.executeUpdate();
      		return "{\"success\":true}";
         } catch (SQLException e) {
            return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
        }     
      }
    public String sendMessage(String senderID, int roomID,Time time, String txt, String img){
        // Here's a bit of useful code, use it or delete it 
      	try (PreparedStatement ps = conn.prepareStatement("INSERT INTO message VALUES(?,?,?,?,?)");){
      		ps.setString(1, senderID);
      		ps.setInt(2, roomID);
      		ps.setTime(3, time);
      		ps.setString(4, txt);
      		ps.setString(5, img);
      		ps.executeUpdate();
      		return "{\"success\":true}";
         } catch (SQLException e) {
            return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
        }     
      }
    public String removeMessage(String senderID, int roomID,Time time){
        // Here's a bit of useful code, use it or delete it 
      	try (PreparedStatement ps = conn.prepareStatement("DELETE FROM message WHERE senderID=? AND roomID=? AND time=?");){
      		ps.setString(1, senderID);
      		ps.setInt(2, roomID);
      		ps.setTime(3, time);
      		ps.executeUpdate();
      		return "{\"success\":true}";
         } catch (SQLException e) {
            return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
        }     
      }
    // Return a JSON document containing lots of information about a student, it should validate against the schema found in information_schema.json
    public String getChatRooms(String username) throws SQLException{
        
        try(PreparedStatement st = conn.prepareStatement(
            // replace this with something more useful
            "SELECT jsonb_build_object('Chatrooms',Chatroom) AS jsondata FROM BasicInformation WHERE username=?"
            );){
            
            st.setString(1, username);
            
            ResultSet rs = st.executeQuery();
            
            if(rs.next())
              return rs.getString("jsondata");
            else
              return "{\"user\":\"does not exist :(\"}"; 
            
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
