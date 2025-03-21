package Server;

import Client.Observer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class DataLogic extends UnicastRemoteObject implements Subject {
    
    private DatabaseHandler databaseHandler = new DatabaseHandler();
    
    private static final long serialVersionUID = 1L;
    private List<Observer> observers = new ArrayList<>();
    
    private UserHandler userHandler = new UserHandler(databaseHandler);
    private ChatroomHandler chatroomHandler = new ChatroomHandler(databaseHandler);
    private MessageHandler messageHandler = new MessageHandler(databaseHandler);
    
    public DataLogic() throws RemoteException {
        super();
    }
    
    public void createUser(User user) throws RemoteException {
        userHandler.createUser(user);
        notifySubscribers();
    }
    
    public void deleteUser(User user) throws RemoteException {
        userHandler.deleteUser(user);
        chatroomHandler.deleteUserFromChatrooms(user);
        notifySubscribers();
    }

    public boolean authenticateUser(User user) throws RemoteException {
        return userHandler.authenticateUser(user);
    }

    public void createChatroom(Chatroom chatroom) throws RemoteException {
        chatroomHandler.createChatroom(chatroom);
        notifySubscribers();
    }

    public void deleteChatroom(Chatroom chatroom) throws RemoteException {
        chatroomHandler.deleteChatroom(chatroom);
        notifySubscribers();
    }
    public void createMessage(Message message) throws RemoteException {
        chatroomHandler.createMessage(message);
        notifySubscribers();
    }

    public void deleteMessage(Message message) throws RemoteException {
        chatroomHandler.deleteMessage(message);
        notifySubscribers();
    }

    public void addChatroomUser(User user, Chatroom chatroom) throws RemoteException {
        chatroomHandler.addChatroomUser(user, chatroom);
        notifySubscribers();
    }

    public void removeChatroomUser(User user, Chatroom chatroom) throws RemoteException {
        chatroomHandler.removeChatroomUser(user, chatroom);
        notifySubscribers();
    }
    
    public byte[] getImageFile(Message message) throws RemoteException {
        return messageHandler.getImageFile(message);
    }
    
    public void uploadImage(File image, int roomID){
        messageHandler.uploadImage(image, roomID);
    }

    public LinkedList<Message> chatHistory(Chatroom chatroom) throws RemoteException{
        return chatroomHandler.chatHistory(chatroom);
    }
    
    public String messageInfo(Message message) throws RemoteException{
        return userHandler.messageInfo(message);
    }
    
    public LinkedList<User> chatroomUsers(Chatroom chatroom) throws RemoteException{
        return chatroomHandler.chatroomUsers(chatroom);
    }
     
    public LinkedList<Chatroom> userChatrooms(User user) throws RemoteException {
        return chatroomHandler.userChatrooms(user);
    }
    
    public int getUserID(User user) throws RemoteException {
        return databaseHandler.getUserID(user);
    }
    
    public int getRoomID(Chatroom chatroom) throws RemoteException {
        return databaseHandler.getRoomID(chatroom);
    }
    
    public boolean inChatroom(User user, Chatroom chatroom) throws RemoteException {
        return chatroomHandler.inChatroom(user, chatroom);
    }
    
    public LinkedList<User> getUsers() {
        return databaseHandler.getAllUsers();
    }
    
    public LinkedList<Chatroom> getChatrooms() {
        return databaseHandler.getAllChatrooms();
    }
    
    public int nextAvailableMessageID(Chatroom chatroom) throws RemoteException {
        return databaseHandler.nextAvailableMessageID(chatroom);
    }
    
    public void addSubscriber(Observer observer) throws RemoteException {
        observers.add(observer);
    }
    
    public void removeSubscriber(Observer observer) throws RemoteException {
        observers.remove(observer);
    }
    
    public void notifySubscribers() throws RemoteException {
        for (Observer observer : observers) {
            try {
                observer.update();
                System.out.println("Observers notified");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    } 
}