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
                String chatroomName = parts[1];
                dataLogic.deleteChatroom(chatroomName);
                out.println("Chatroom deleted successfully.");
            }

            if (clientMessage.startsWith("CREATE_MESSAGE")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                String chatroomName = parts[2];
                String timestamp = parts[3];
                String text = parts[4];
                dataLogic.createMessage(username, chatroomName, timestamp, text);
                out.println("Message created successfully.");
            }
            
            if (clientMessage.startsWith("DELETE_MESSAGE")) {
                String[] parts = clientMessage.split(" ");
                int messageID = Integer.parseInt(parts[1]);
                String chatroomName = parts[2];
                dataLogic.deleteMessage(messageID, chatroomName);
                out.println("Message deleted successfully.");
            }
            
            if (clientMessage.startsWith("ADD_CHATROOM_USER")) {
                String[] parts = clientMessage.split(" ");
                String username = parts[1];
                String chatroomName = parts[2];
                dataLogic.addChatroomUser(username, chatroomName);
                out.println("User added to chatroom successfully.");
            }
            
            if (clientMessage.startsWith("REMOVE_CHATROOM_USER")) {
                String[] parts = clientMessage.split(" ");
                int userID = Integer.parseInt(parts[1]);
                String chatroomName = parts[2];
                dataLogic.removeChatroomUser(userID, chatroomName);
                out.println("User removed from chatroom successfully.");
            }
            
            if (clientMessage.startsWith("GET_CHATROOM_USERS")) {
                String[] parts = clientMessage.split(" ");
                String chatroomName = parts[1];
                LinkedList<User> chatroomUsers = dataLogic.chatroomUsers(chatroomName);
                out.println("Chatroom users retrieved successfully.");
            }
            
            if (clientMessage.startsWith("GET_CHAT_HISTORY")) {
                String[] parts = clientMessage.split(" ");
                String chatroomName = parts[1];
                LinkedList<Message> chatHistory = dataLogic.chatHistory(chatroomName);   
                out.println("Chat history retrieved successfully.");
            }
        }
    }

    public void createUser(String username, String password) {
        User user = new User(username, password);
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
                    chatroom.removeChatroomUser(user); // Remove the user from chatrooms
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

    public void deleteChatroom(String chatroomName) {
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getName().equals(chatroomName)) {
                dh.unregisterChatroom(chatroom);
                chatrooms.remove(chatroom);
                break;
            }
        }
        notifySubscribers();
    }

    public void createMessage(String username, String chatroomName, String timestamp, String text) {

        int userID = 0;
        
        for (User user : users) {
            if (user.getUsername() == username) {
                userID = user.getUserID();
            }
        }
        
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getName().equals(chatroomName)) {
                int messageID = dh.nextAvailableMessageID(chatroom);
                int roomID = chatroom.getRoomID();
                Message message = new Message(messageID, userID, roomID, timestamp, text);
                dh.addMessage(message);
                chatroom.addMessage(message); //denna raden gör att programmet inte funkar
                break;
            }
        }
        notifySubscribers();
    }

    public void deleteMessage(int messageID, String chatroomName) {
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getName().equals(chatroomName)) {
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

    public void addChatroomUser(String username, String chatroomName) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                for (Chatroom chatroom : chatrooms) {
                    if (chatroom.getName().equals(chatroomName)) {
                        dh.registerChatroomUser(user, chatroom);
                        chatroom.addChatroomUser(user);
                        break;
                    }
                }
            }
        }
        notifySubscribers();
    }

    public void removeChatroomUser(int userID, String chatroomName) {
        for (User user : users) {
            if (user.getUserID() == userID) {
                for (Chatroom chatroom : chatrooms) {
                    if (chatroom.getName().equals(chatroomName)) {
                        dh.unregisterChatroomUser(user, chatroom);
                        chatroom.removeChatroomUser(user);
                        break;
                    }
                }
            }
        }
        notifySubscribers();
    }

    public LinkedList<User> chatroomUsers(String chatroomName) {
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getName().equals(chatroomName)) {
                return chatroom.getChatroomUsers();
            }
        }
        return new LinkedList<>();
    }

    public LinkedList<Message> chatHistory(String chatroomName) {
        for (Chatroom chatroom : chatrooms) {
            if (chatroom.getName().equals(chatroomName)) {
                return chatroom.getChatHistory();
            }
        }
        return new LinkedList<>();
    }
    
    public String messageInfo(Message message) {
        for (User user : users) {
            if (user.getUserID() == message.getSenderID()) {
                return user.getUserID() + ": " + message.getText();
            }
        }
        return "Deleted User: " + message.getSenderID();
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
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
