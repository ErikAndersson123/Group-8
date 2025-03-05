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
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == message.getRoomID()) {
                message.setMessageID(dh.nextAvailableMessageID(c));
                dh.addMessage(message);
                c.addMessage(message);
                //c.setChatHistory(dh.getAllMessages(c));
            }
        }
        System.out.println("Message created");
        notifySubscribers();
    }

    public void deleteMessage(Message message) throws RemoteException {
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == message.getRoomID()) {
                dh.removeMessage(message);
                c.removeMessage(message);
                //c.setChatHistory(dh.getAllMessages(c));
            }
        }
        System.out.println("Message deleted");
        notifySubscribers();
    }

    public void addChatroomUser(User user, Chatroom chatroom) throws RemoteException {
        dh.registerChatroomUser(user, chatroom);
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                System.out.println("Hallå hallå");
                //c.addChatroomUser(user);
                c.setChatroomUsers(dh.getAllChatroomUsers(chatroom));
            }
        }
        System.out.println("User added to chatroom");
        notifySubscribers();
    }

    public void removeChatroomUser(User user, Chatroom chatroom) throws RemoteException {
        dh.unregisterChatroomUser(user, chatroom);
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                System.out.println("Hallå hallå hallååååååååå");
                //c.removeChatroomUser(user);
                c.setChatroomUsers(dh.getAllChatroomUsers(chatroom));
            }
        }
        System.out.println("User removed from chatroom");
        notifySubscribers();
    }
    
    
    
    
    
    
    
    
    
    
    public byte[] getImageFile(Message msg) throws RemoteException {

        String imageFilename = dh.getImagePath(msg);
        System.out.println(imageFilename);

        if (imageFilename == null) {
            System.out.println("Image path is NULL for message ID: " + msg.getMessageID()+ " in roomId: " + msg.getRoomID());
            return null;
        }

        String baseDir = "C:\\Users\\carpe\\OneDrive\\Desktop\\Group-8-Eriks-Branch-nr-19\\";
        String imagePath = baseDir +imageFilename;

        System.out.println(imagePath);
    
        File file = new File(imagePath);
        if (!file.exists()) {
            System.out.println("Image file not found at: " + imagePath);
            return null;
        }
    
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileData = new byte[(int) file.length()];
            fis.read(fileData);
            return fileData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void uploadImage(File image){
        String imageName = image.getName();
        String outputPath = imageName;

        try (FileInputStream fis = new FileInputStream(image);
             FileOutputStream fos = new FileOutputStream(outputPath)) {

            byte[] imageData = new byte[(int) image.length()];
            fis.read(imageData);  // Read image into byte array
            fos.write(imageData); // Save to new file

            System.out.println("Image received and saved as: " + outputPath);
            
        } catch (IOException ex) {
            System.out.println("Error processing image: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    
    
    
    



    public LinkedList<Message> chatHistory(Chatroom chatroom) throws RemoteException{
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                System.out.println("Chat history sent");
                return c.getChatHistory();
            }
        }
        System.out.println("Chat history sent");
        return null;
    }
    
    public String messageInfo(Message message) throws RemoteException{

        
        for (User u : users) {
            if (u.getUserID() == message.getSenderID()) {
                return u.getUsername();
            }
        }
        return "Deleted User";
        /*
        LinkedList<String> messageInfo = new LinkedList<String>();
        
        for (User user : users) {
            if (user.getUserID() == message.getSenderID()) {
                messageInfo += user.getUsername() + ": " + message.getText());
            }
            else {
                messageInfo.add("Deleted User: " + message.getText());
            }
        }
        System.out.println("Message Info sent");
        return messageInfo;*/
    }
    
    public LinkedList<User> chatroomUsers(Chatroom chatroom) throws RemoteException{
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                System.out.println("Chatroom Users sent");
                return c.getChatroomUsers();
            }
        }
        System.out.println("Chatroom Users sent");
        return null;
    }
     
    public LinkedList<Chatroom> userChatrooms(User user) throws RemoteException {
        LinkedList<Chatroom> list = new LinkedList<>();
        for (Chatroom c: chatrooms) {
            for (User u : c.getChatroomUsers()) {
                if (u.getUserID() == user.getUserID()) {
                    list.add(c);
                }
            }
        }
        System.out.println("User chatrooms sent");
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


    
    
    
    
    public int getUserID(User user) throws RemoteException {
        return dh.getUserID(user);
    }
    
    public int getRoomID(Chatroom chatroom) throws RemoteException {
        return dh.getRoomID(chatroom);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public boolean inChatroom(User user, Chatroom chatroom) throws RemoteException {        
        for (Chatroom c : userChatrooms(user)) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    
    public LinkedList<User> getUsers() {
        return this.users;
    }
    
    public LinkedList<Chatroom> getChatrooms() {
        return this.chatrooms;
    }
    
    
    
    
    
    
    
    public void addSubscriber(Observer observer) throws RemoteException {
        observers.add(observer);
    }
    
    public void removeSubscriber(Observer observer) throws RemoteException {
        observers.remove(observer);
    }
    
    public void notifySubscribers() throws RemoteException {
        System.out.println("Hej");
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