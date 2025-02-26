package server;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import client.Observer;

public class DataLogic {

    private DatabaseHandler dh = new DatabaseHandler();
    private LinkedList<Observer> observers = new LinkedList<>();
    
    private LinkedList<User> users = new LinkedList<>();
    private LinkedList<Chatroom> chatrooms = new LinkedList<>();
    
    public static void main(String[] args) {
        DataLogic server = new DataLogic();
        server.startServer(8080);
    }

    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            
            loadUsersFromDatabase();
            loadChatroomsFromDatabase();
            loadMessagesFromDatabase();
            loadChatroomUsersFromDatabase();
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress());
                
                new ClientHandler(socket, this).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private DataLogic dataLogic;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket, DataLogic dataLogic) {
            this.socket = socket;
            this.dataLogic = dataLogic;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    System.out.println("Received from client: " + clientMessage);
                    handleClientRequest(clientMessage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleClientRequest(String clientMessage) {
            if (clientMessage.startsWith("CREATE_USER")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                String password = parts[2];
                dataLogic.createUser(username, password);
                out.println("User created successfully.");
            }
            
            if (clientMessage.startsWith("DELETE_USER")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                dataLogic.deleteUser(username);
                out.println("User deleted successfully.");
            }
            
            if (clientMessage.startsWith("AUTHENTICATE_USER")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                String password = parts[2];
                if (dataLogic.authenticateUser(username, password)) {
                    out.println("User authenticated successfully.");
                }
            }
            
            if (clientMessage.startsWith("CREATE_CHATROOM")) {
                String[] parts = clientMessage.split(" ");
                String chatroomName = parts[1];
                dataLogic.createChatroom(chatroomName);
                out.println("Chatroom created successfully.");
            }
            
            if (clientMessage.startsWith("DELETE_CHATROOM")) {
                String[] parts = clientMessage.split(" ");
                int roomID = Integer.parseInt(parts[1]);
                dataLogic.deleteChatroom(roomID);
                out.println("Chatroom deleted successfully.");
            }

            if (clientMessage.startsWith("CREATE_MESSAGE")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                int roomID = Integer.parseInt(parts[2]);
                String timestamp = parts[3];
                String text = parts[4];
                String image = parts[5];
                dataLogic.createMessage(username, roomID, timestamp, text, image); //it doesn reach this method
                out.println("Message created successfully.");
            }
            
            if (clientMessage.startsWith("DELETE_MESSAGE")) {
                String[] parts = clientMessage.split(" ");
                int messageID = Integer.parseInt(parts[1]);
                int roomID = Integer.parseInt(parts[2]);
                dataLogic.deleteMessage(messageID, roomID);
                out.println("Message deleted successfully.");
            }
            
            if (clientMessage.startsWith("ADD_CHATROOM_USER")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                int roomID = Integer.parseInt(parts[2]);
                dataLogic.addChatroomUser(username, roomID);
                out.println("User added to chatroom successfully.");
            }
            
            if (clientMessage.startsWith("REMOVE_CHATROOM_USER")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                int roomID = Integer.parseInt(parts[2]);
                dataLogic.removeChatroomUser(username, roomID);
                out.println("User removed from chatroom successfully.");
            }
            
            if (clientMessage.startsWith("GET_CHAT_HISTORY")) {
                String[] parts = clientMessage.split(" ");
                int roomID = Integer.parseInt(parts[1]);
                LinkedList<Message> chatHistory = dataLogic.chatHistory(roomID);   
                String messageInfo = "";

                for (Message message : chatHistory) {
                    messageInfo += messageInfo(message) + " ";
                }
                
                out.println(messageInfo);
            }
            
            if (clientMessage.startsWith("GET_CHATROOM_USERS")) {
                String[] parts = clientMessage.split(" ");
                int roomID = Integer.parseInt(parts[1]);
                String chatroomUsers = dataLogic.chatroomUsers(roomID);
                out.println(chatroomUsers);
            }
            
            if (clientMessage.startsWith("GET_USER_CHATROOMS")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                String userChatrooms = dataLogic.userChatrooms(username);
                out.println(userChatrooms);
            }
        }
    }

    public void createUser(String username, String password) {
        User user = new User(0, username, password);
        dh.registerUser(user);
        user.setUserID(dh.getUserID(user));
        users.add(user);
        notifySubscribers();
    }
    
    public void deleteUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                dh.unregisterUser(user);
                users.remove(user);
                for (Chatroom chatroom : chatrooms) {
                    chatroom.removeChatroomUser(user);
                }
                break;
            }
        }
        notifySubscribers();
    }

    public boolean authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void createChatroom(String chatroomName) {
        int roomID = dh.nextAvailableRoomID();
        Chatroom chatroom = new Chatroom(roomID, chatroomName);
        dh.registerChatroom(chatroom);
        chatrooms.add(chatroom);
        notifySubscribers();
    }

    public void deleteChatroom(int roomID) {
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getRoomID() == roomID) {
                dh.unregisterChatroom(chatroom);
                chatrooms.remove(chatroom);
                break;
            }
        }
        notifySubscribers();
    }

    public void createMessage(String username, int roomID, String timestamp, String text, String image) {

        int userID = 0;
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                userID = user.getUserID();
            }
        }
        
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getRoomID() == roomID) {
                int messageID = dh.nextAvailableMessageID(chatroom);
                Message message = new Message(messageID, userID, roomID, timestamp, text, image);
                dh.addMessage(message);
                chatroom.addMessage(message);
                break;
            }
        }
        notifySubscribers();
    }

    public void deleteMessage(int messageID, int roomID) {
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getRoomID() == roomID) {
                for (Message message : chatroom.getChatHistory()) {
                    if (message.getMessageID() == messageID) {
                        dh.removeMessage(message);
                        chatroom.removeMessage(message);
                        break;
                    }
                }
            }
        }
        notifySubscribers();
    }

    public void addChatroomUser(String username, int roomID) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                for (Chatroom chatroom : chatrooms) {
                    if (chatroom.getRoomID() == roomID) {
                        dh.registerChatroomUser(user, chatroom);
                        chatroom.addChatroomUser(user);
                        break;
                    }
                }
            }
        }
        notifySubscribers();
    }

    public void removeChatroomUser(String username, int roomID) {
        
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                for (Chatroom chatroom : chatrooms) {
                    if (chatroom.getRoomID() == roomID) {
                        dh.unregisterChatroomUser(user, chatroom);
                        chatroom.removeChatroomUser(user);
                        break;
                    }
                }
            }
        }
        notifySubscribers();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    



    public LinkedList<Message> chatHistory(int roomID) {
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getRoomID() == roomID) {
                return chatroom.getChatHistory();
            }
        }
        return new LinkedList<>();
    }
    
    public String messageInfo(Message message) {
        for (User user : users) {
            if (user.getUserID() == message.getSenderID()) {
                return user.getUsername() + ": " + message.getText();
            }
        }
        return "Deleted User: " + message.getText();
    }
    
    public String chatroomUsers(int roomID) {
        String string = "";
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getRoomID() == roomID) {
                for (User user : chatroom.getChatroomUsers()) {
                    string += user.getUsername() + " ";
                }
            }
        }
        return string;
    }
        
    public String userChatrooms(String username) {
        String string = "";
        for (Chatroom chatroom : chatrooms) {
            for (User user : chatroom.getChatroomUsers()) {
                if (user.getUsername().equals(username)) {
                    string += chatroom.getRoomID() + " ";
                }
            }
        }
        return string;
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


    
    
    
    
    
    
    
    
    
    
    
    

    public void addSubscriber(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeSubscriber(Observer observer) {
        observers.remove(observer);
    }

    public void notifySubscribers() {
        
        for (User user : users) {
            System.out.println(user.getUserID() + " " + user.getUsername() + " " + user.getPassword());
        }
        
        System.out.println();
        
        for (Chatroom chatroom : chatrooms) {
            System.out.println(chatroom.getRoomID() + " " + chatroom.getName());
        }
        
        System.out.println();
        
        for (Chatroom chatroom : chatrooms) {
            for (Message message : chatroom.getChatHistory()) {
                System.out.println(message.getMessageID() + " " + message.getSenderID() + " " + 
                message.getRoomID() + " " + message.getTimestamp() + " " + message.getText());
            }
        }
        
        System.out.println();
        
        for (Chatroom chatroom : chatrooms) {
            for (User user : chatroom.getChatroomUsers()) {
                System.out.println(user.getUserID() + " " + chatroom.getRoomID());
            }
        }
        
        System.out.println();
        
        for (Chatroom chatroom : chatrooms) {
            for (Message message : chatroom.getChatHistory()) {
                System.out.println(messageInfo(message));   
            }
        }
        
        System.out.println();        
        
        
        for (Observer observer : observers) {
            observer.update();
        }
    }
}

