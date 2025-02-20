package server;

import java.util.LinkedList;

public class Chatroom {
    private int roomID;
    private LinkedList<User> users;
    private LinkedList<Message> history;
    
    public Chatroom(int roomID) {
        this.roomID = roomID;
    }
    
    public int getRoomID() {
        return roomID;
    }
    
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    
    public LinkedList<User> getUsers() {
        return users;
    }
    
    public void addUser(User user) {
        this.users.add(user);
    }
    
    public void removeUser(User user) {
        this.users.remove(user);
    }
    
    public LinkedList<Message> getMessages() {
        return history;
    }
    
    public void addMessage(Message message) {
        this.history.add(message);
    }
    
    public void removeMessage(Message message) {
        this.history.remove(message);
    }
}