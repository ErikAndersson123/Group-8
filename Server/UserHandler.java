package Server;

import java.util.*;

public class UserHandler {
    
    private DatabaseHandler databaseHandler;
    
    private LinkedList<User> users = new LinkedList<>();
    
    public UserHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        loadUsersFromDatabase();
    }
    
    public void createUser(User user) {
        databaseHandler.registerUser(user);
        user.setUserID(databaseHandler.getUserID(user));
        users.add(user);
        System.out.println("User created");
    }
    
    public void deleteUser(User user) {
        databaseHandler.unregisterUser(user);
        users.remove(user);
        System.out.println("User deleted");
    }
    
    public boolean authenticateUser(User user) {     
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                System.out.println("User authorized");
                return true;
            }
        }
        System.out.println("User not authorized");
        return false;
    }
    
    public String messageInfo(Message message) {
        for (User u : users) {
            if (u.getUserID() == message.getSenderID()) {
                return u.getUsername();
            }
        }
        return "Deleted User";
    }
    
    public void loadUsersFromDatabase() {
        users.clear();
        users.addAll(databaseHandler.getAllUsers());
    }
}