package Server;

import Client.Observer;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;


public interface Subject extends Remote {
    
    public void createUser(User user) throws RemoteException;
    public void deleteUser(User user) throws RemoteException;
    public boolean authenticateUser(User user) throws RemoteException;
    public void createChatroom(Chatroom chatroom) throws RemoteException;
    public void deleteChatroom(Chatroom chatroom) throws RemoteException;
    public void createMessage(Message message) throws RemoteException;
    public void deleteMessage(Message message) throws RemoteException;
    public void addChatroomUser(User user, Chatroom chatroom) throws RemoteException;
    public void removeChatroomUser(User user, Chatroom chatroom) throws RemoteException;
    public LinkedList<Message> chatHistory(Chatroom chatroom) throws RemoteException;
    public String messageInfo(Message message) throws RemoteException;
    public LinkedList<User> chatroomUsers(Chatroom chatroom) throws RemoteException;
    public LinkedList<Chatroom> userChatrooms(User user) throws RemoteException;
    public int getUserID(User user) throws RemoteException;
    public int getRoomID(Chatroom chatroom) throws RemoteException;
    public boolean inChatroom(User user, Chatroom chatroom) throws RemoteException;
    
    
    
    void addSubscriber(Observer observer) throws RemoteException;
    void removeSubscriber(Observer observer) throws RemoteException;
    void notifySubscribers() throws RemoteException;
    public LinkedList<Chatroom> getChatrooms() throws RemoteException;
    public LinkedList<User> getUsers() throws RemoteException;
    public byte[] getImageFile(Message msg) throws RemoteException;
    public void uploadImage(File image) throws RemoteException;
}