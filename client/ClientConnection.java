package client;

import java.io.*;
import java.net.*;

public class ClientConnection {
 
    private static final String SERVER_ADDRESS = "10.0.1.21";
    private static final int SERVER_PORT = 8080;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
   
    public static void startConnection() throws Exception {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    
    public static void endConnection() throws Exception {
        socket.close();
    }

    public static void main(String[] args) throws Exception{
       
        createUser("Pelle", "123");
        createUser("Kalle", "123");
     
        String test = authenticateUser("Pelle", "123");
         
        createChatroom("Chat6");
     
        addChatroomUser("Pelle", 6);
        addChatroomUser("Kalle", 6);
         
        createMessage("Pelle", 6, System.currentTimeMillis(), "Hej", null);
        createMessage("Kalle", 6, System.currentTimeMillis(), "Hej", null);
       
        pause();
  
        test = getChatroomUsers(6);
        test = getUserChatrooms("Pelle");
        test = getChatHistory(6);

        pause();
         
        deleteUser("Pelle");
        deleteUser("Kalle");
         
        pause();
     
        deleteChatroom(6);
    }

    // Method to create a new user
    public static void createUser(String username, String password) throws Exception {
        String instruction = "CREATE_USER " + username + " " + password;
        sendInstruction(instruction);
    }

    // Method to delete a user
    public static void deleteUser(String username) throws Exception {
        String instruction = "DELETE_USER " + username;
        sendInstruction(instruction);
    }

    // Method to authenticate a user
    public static String authenticateUser(String username, String password) throws Exception {
        String instruction = "AUTHENTICATE_USER " + username + " " + password;
        return getInfo(instruction);
    }

    // Method to create a chatroom
    public static void createChatroom(String name) throws Exception {
        String instruction = "CREATE_CHATROOM " + name;
        sendInstruction(instruction);
    }

    // Method to delete a chatroom
    public static void deleteChatroom(int roomID) throws Exception {
        String instruction = "DELETE_CHATROOM " + roomID;
        sendInstruction(instruction);
    }

    // Method to create a message in a chatroom
    public static void createMessage(String username, int roomID, long timestamp, String text, String image) throws Exception {
        String instruction = "CREATE_MESSAGE " + username + " " + roomID + " " + timestamp + " " + text + " " + image;
        sendInstruction(instruction);
    }
 
    // Method to delete a message in a chatroom
    public static void deleteMessage(int messageID, int roomID) throws Exception {
        String instruction = "DELETE_MESSAGE " + messageID + " " + roomID;
        sendInstruction(instruction);
    }
 
    //Method to add a user to a chatroom
    public static void addChatroomUser(String username, int roomID) throws Exception {
        String instruction = "ADD_CHATROOM_USER " + username + " " + roomID;
        sendInstruction(instruction);
    }
 
    //Method to delete a user from a chatroom
    public static void deleteChatroomUser(String username, int roomID) throws Exception {
        String instruction = "REMOVE_CHATROOM_USER " + username + " " + roomID;
        sendInstruction(instruction);
    }
 
    //Method to get the chat history
    public static String getChatHistory(int roomID) throws Exception {
        String instruction = "GET_CHAT_HISTORY " + roomID;
        return getInfo(instruction);
    }
   
    //Method to get a list of all users from a chatroom
    public static String getChatroomUsers(int roomID) throws Exception {
        String instruction = "GET_CHATROOM_USERS " + roomID;
        return getInfo(instruction);
    }
   
    //Method to get a list of all users from a chatroom
    public static String getUserChatrooms(String username) throws Exception {
        String instruction = "GET_USER_CHATROOMS " + username;
        return getInfo(instruction);
    }
 
    // Method to send an instruction to the server
    private static void sendInstruction(String instruction) throws Exception {
        startConnection();
        try {
            out.println(instruction);  // Send message to server
            String response = in.readLine();  // Read the server's response
            System.out.println("Server response: " + response);  // Print response from the server
        } catch (IOException e) {
            e.printStackTrace();
        }
        endConnection();
    }
   
    private static String getInfo(String instruction) throws Exception {
        startConnection();
        String response = "";
        try {
            out.println(instruction);  // Send message to server
            response = in.readLine();  // Read the server's response
            System.out.println("Server response: " + response);  // Print response from the server
        } catch (IOException e) {
            e.printStackTrace();
        }
        endConnection();
        return response;
    }
   
 
    public static void pause() throws Exception {
        System.out.println("PRESS ENTER");
        while(System.in.read() != '\n');
    }
}