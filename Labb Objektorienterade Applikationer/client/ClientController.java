package client;

import java.sql.SQLException;
import java.sql.Time;
import java.util.LinkedList;

import server.Message;
import server.User;

public class ClientController {
    
    public void Sendmsg(String time, String userID, /***/String img, String text, int RoomID) throws ClassNotFoundException, SQLException {
        Message msg = new Message(userID, RoomID,time,text,img);
    }
    public LinkedList<Message> retrieveHistory(int RoomID){
        return null;
        
    }
    public void createChatroom(User[] users) {
        
    }
    public void leaveChatroom(User user) {
        
    }
    public void invite(User user, int RoomID) {
        
    }
}
