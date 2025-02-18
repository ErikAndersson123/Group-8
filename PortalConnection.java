
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


    // Register a student on a course, returns a tiny JSON document (as a String)
    
    

    
    
    
    public String register(String username, String password){
      
    String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, username);
        stmt.setString(2, password);

        stmt.executeUpdate();
        
        return "{\"success\":true}";
 
      } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }     
    }

    public String unregister(String username, String password){
        
    String sql = "DELETE FROM Users WHERE student = '" + username + "' AND course = '" + password + "'";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        //stmt.setString(1, student);
        //stmt.setString(2, courseCode);
        
        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected == 0) {
            return "{\"success\":false, \"error\":\"Student not registered for this course or course does not exist\"}";
        }        
        
        return "{\"success\":true}";
 
      } catch (SQLException e) {
          return "{\"success\":false, \"error\":\""+getError(e)+"\"}";
      }
    }

    // Return a JSON document containing lots of information about a student, it should validate against the schema found in information_schema.json
    public String getInfo(String student) throws SQLException{
        
        try(PreparedStatement st = conn.prepareStatement(
            // replace this with something more useful
            //"SELECT jsonb_build_object('student',idnr,'name',name) AS jsondata FROM BasicInformation WHERE idnr=?"
            //);)
            
            "SELECT json_build_object( " +
             "    'student', bi.idnr, " +
             "    'name', bi.name, " +
             "    'login', bi.login, " +
             "    'program', bi.program, " +
             "    'branch', bi.branch, " +
             "    'finished', (SELECT COALESCE(jsonb_agg(json_build_object( " +
             "            'course', courseName, " +
             "            'code', course, " +
             "            'credits', credits, " +
             "            'grade', grade)), '[]') " +
             "        FROM FinishedCourses " +
             "        WHERE student = bi.idnr), " +
             "    'registered', (SELECT COALESCE(jsonb_agg(json_build_object( " +
             "            'course', c.name, " +
             "            'code', c.code, " +
             "            'status', r.status, " +
             "            'position', wl.position)), '[]') " +
             "        FROM Registrations r " +
             "        LEFT JOIN WaitingList wl " +
             "        ON wl.student = bi.idnr " +
             "        AND wl.course = r.course " +
             "        LEFT JOIN Courses c " +
             "        ON r.course = c.code " + 
             "        WHERE r.student = bi.idnr), " +
             "    'seminarCourses', pg.seminarCourses, " +
             "    'mathCredits', pg.mathCredits, " +
             "    'totalCredits', pg.totalCredits, " +
             "    'canGraduate', pg.qualified " +
             ") " +
             " AS jsondata " + 
             "FROM BasicInformation bi " +
             "LEFT JOIN PathToGraduation pg ON pg.student = bi.idnr " +
             "WHERE bi.idnr = ? " + // Use parameterized value for student ID
             "GROUP BY bi.idnr, bi.name, bi.login, bi.program, bi.branch, pg.seminarCourses, pg.mathCredits, pg.totalCredits, pg.qualified;");)

            
            

            
            
            {
            
            st.setString(1, student);
            
            ResultSet rs = st.executeQuery();
            
            if(rs.next())
              return rs.getString("jsondata");
            else
              return "{\"student\":\"does not exist :(\"}"; 
            
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