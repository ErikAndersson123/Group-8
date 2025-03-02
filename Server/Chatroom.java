package Server;

import java.io.Serializable;
import java.util.LinkedList;

public class Chatroom implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int roomID;
    private String chatroomName;
    private LinkedList<User> chatroomUsers;
    private LinkedList<Message> chatHistory;
    
    public Chatroom(String chatroomName) {
        this.chatroomName = chatroomName;
        this.chatroomUsers = new LinkedList<>();
        this.chatHistory = new LinkedList<>();
    }

    public Chatroom(int roomID, String chatroomName) {
        this.roomID = roomID;
        this.chatroomName = chatroomName;
        this.chatroomUsers = new LinkedList<>();
        this.chatHistory = new LinkedList<>();
    }

    public int getRoomID() {
        return roomID;
    }
    
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    
    public String getName() {
        return chatroomName;
    }

    public void setName(String name) {
        this.chatroomName = name;
    }

    public void addChatroomUser(User user) {
        if (!chatroomUsers.contains(user)) {
            chatroomUsers.add(user);
        }
    }

    public void removeChatroomUser(User user) {
        chatroomUsers.remove(user);
    }

    public LinkedList<User> getChatroomUsers() {
        return chatroomUsers;
    }
    
    public void setChatroomUsers(LinkedList<User> chatroomUsers) {
        this.chatroomUsers = chatroomUsers;
    }
    
    public void addMessage(Message message) {
        if (!chatHistory.contains(message)) {
            chatHistory.add(message); 
        }
    }

    public void removeMessage(Message message) {
        chatHistory.remove(message);
    }

    public LinkedList<Message> getChatHistory() {
        return chatHistory;
    }
    
    public void setChatHistory(LinkedList<Message> chatHistory) {
        this.chatHistory = chatHistory;
    }
}
