package Server;

import Client.Observer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class DataLogic extends UnicastRemoteObject implements Subject {
    
    private DatabaseHandler dh = new DatabaseHandler();
    
    private static final long serialVersionUID = 1L;
    private List<Observer> observers = new ArrayList<>();
    private LinkedList<User> users = new LinkedList<>();
    private LinkedList<Chatroom> chatrooms = new LinkedList<>();
    
    public DataLogic() throws RemoteException {
        super();
        loadUsersFromDatabase();
        loadChatroomsFromDatabase();
        loadMessagesFromDatabase();
        loadChatroomUsersFromDatabase();
    }
    
    public void createUser(User user) throws RemoteException {
        dh.registerUser(user);
        user.setUserID(dh.getUserID(user));
        users.add(user);
        System.out.println("User created");
        notifySubscribers();
    }
    
    public void deleteUser(User user) throws RemoteException {
        dh.unregisterUser(user);
        users.remove(user);
        for (Chatroom chatroom : chatrooms) {
            chatroom.removeChatroomUser(user);
        }
        System.out.println("User deleted");
        notifySubscribers();
    }

    public boolean authenticateUser(User user) throws RemoteException {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                System.out.println("User authorized");
                return true;
            }
        }
        System.out.println("User not authorized");
        return false;
    }

    public void createChatroom(Chatroom chatroom) throws RemoteException {
        chatroom.setRoomID(dh.nextAvailableRoomID());
        dh.registerChatroom(chatroom);
        chatrooms.add(chatroom);
        System.out.println("Chatroom created");
        notifySubscribers();
    }

    public void deleteChatroom(Chatroom chatroom) throws RemoteException {
        dh.unregisterChatroom(chatroom);
        chatrooms.remove(chatroom);
        System.out.println("Chatroom deleted");
        notifySubscribers();
    }

    public void createMessage(Message message) throws RemoteException {
        dh.addMessage(message);
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == message.getRoomID()) {
                c.addMessage(message);
            }
        }
        System.out.println("Message created");
        notifySubscribers();
    }

    public void deleteMessage(Message message) throws RemoteException {
        dh.removeMessage(message);
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == message.getRoomID()) {
                c.removeMessage(message);
            }
        }
        System.out.println("Message deleted");
        notifySubscribers();
    }

    public void addChatroomUser(User user, Chatroom chatroom) throws RemoteException {
        dh.registerChatroomUser(user, chatroom);
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                chatroom.addChatroomUser(user);
            }
        }
        System.out.println("User added to chatroom");
        notifySubscribers();
    }

    public void removeChatroomUser(User user, Chatroom chatroom) throws RemoteException {
        dh.unregisterChatroomUser(user, chatroom);
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                chatroom.removeChatroomUser(user);
            }
        }
        System.out.println("User removed from chatroom");
        notifySubscribers();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    



    public LinkedList<Message> chatHistory(Chatroom chatroom) throws RemoteException{
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                System.out.println("Chat history sent");
                return chatroom.getChatHistory();
            }
        }
        System.out.println("Chat history sent");
        return new LinkedList<>();
    }
    
    public LinkedList<String> messageInfo(Message message) throws RemoteException{
        LinkedList<String> messageInfo = new LinkedList<String>();
        for (User user : users) {
            if (user.getUserID() == message.getSenderID()) {
                messageInfo.add(user.getUsername() + ": " + message.getText());
            }
            else {
                messageInfo.add("Deleted User: " + message.getText());
            }
        }
        System.out.println("Message Info sent");
        return messageInfo;
    }
    
    public LinkedList<User> chatroomUsers(Chatroom chatroom) throws RemoteException{
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                System.out.println("Chatroom Users sent");
                return chatroom.getChatroomUsers();
            }
        }
        System.out.println("Chatroom Users sent");
        return null;
    }
        
    public LinkedList<Chatroom> userChatrooms(User user) throws RemoteException{
        LinkedList<Chatroom> list = new LinkedList<>();
        for (Chatroom c : chatrooms) {
            for (User u : c.getChatroomUsers()) {
                if (u.getUserID() == user.getUserID()) {
                    list.add(c);
                }
            }
        }
        System.out.println("User Chatrooms sent");
        return list;
    }
    
 
    
    
    
    
    


    
    
    
    
    
    
    
    
    public void loadUsersFromDatabase() {
        users.clear();
        users.addAll(dh.getAllUsers());
    }
    
    public void loadChatroomsFromDatabase() {
        chatrooms.clear();
        chatrooms.addAll(dh.getAllChatrooms());
    }
    
    public void loadMessagesFromDatabase() {
            for (Chatroom chatroom : chatrooms) {
            LinkedList<Message> messages = dh.getAllMessages(chatroom);
            chatroom.setChatHistory(messages);
        }
    }
    
    public void loadChatroomUsersFromDatabase() {
        for (Chatroom chatroom : chatrooms) {
            LinkedList<User> chatroomUsers = dh.getAllChatroomUsers(chatroom);
            chatroom.setChatroomUsers(chatroomUsers);
        }
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void addSubscriber(Observer observer) throws RemoteException {
        observers.add(observer);
    }
    
    public void removeSubscriber(Observer observer) throws RemoteException {
        observers.remove(observer);
    }
    
    public void notifySubscribers() throws RemoteException {
        for (Observer observer : observers) {
            observer.update();
        }
    } 
}
