package client;

import java.io.*;
import java.net.*;

public class ClientConnection {
   
    private static final String SERVER_ADDRESS = "10.0.1.21";
    private static final int SERVER_PORT = 8080;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) throws Exception{
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        createUser("Pelle", "123");
        createUser("Kalle", "123");
       
        authenticateUser("Pelle", "123");
           
        createChatroom("Chat6");
       
        addChatroomUser("Pelle", "Chat6");
        addChatroomUser("Kalle", "Chat6");
           
        createMessage("Pelle", "Chat6", "2025-02-25", "Hej");
        createMessage("Kalle", "Chat6", "2025-02-25", "Hej");
       

        getChatroomUsers("Chat6");
        getChatHistory("Chat6");
           
        pause();
           
        deleteUser("Pelle");
        deleteUser("Kalle");
           
        pause();
       
        deleteChatroom("Chat6");
       
        getChatroomUsers("Chat6");
        getChatHistory("Chat6");

        socket.close();

    }

    // Method to create a new user
    public static void createUser(String username, String password) {
        String instruction = "CREATE_USER " + username + " " + password;
        sendInstruction(instruction);
    }

    // Method to delete a user
    public static void deleteUser(String username) {
        String instruction = "DELETE_USER " + username;
        sendInstruction(instruction);
    }

    // Method to authenticate a user
    public static void authenticateUser(String username, String password) {
        String instruction = "AUTHENTICATE_USER " + username + " " + password;
        sendInstruction(instruction);
    }

    // Method to create a chatroom
    public static void createChatroom(String name) {
        String instruction = "CREATE_CHATROOM " + name;
        sendInstruction(instruction);
    }

    // Method to delete a chatroom
    public static void deleteChatroom(String name) {
        String instruction = "DELETE_CHATROOM " + name;
        sendInstruction(instruction);
    }

    // Method to create a message in a chatroom
    public static void createMessage(String username, String room, String timestamp, String text) {
        String instruction = "CREATE_MESSAGE " + username + " " + room + " " + timestamp + " " + text;
        sendInstruction(instruction);
    }
   
    // Method to delete a message in a chatroom
    public static void deleteMessage(int messageID, String room) {
        String instruction = "DELETE_MESSAGE " + messageID + " " + room;
        sendInstruction(instruction);
    }
   
    //Method to add a user to a chatroom
    public static void addChatroomUser(String username, String room) {
        String instruction = "ADD_CHATROOM_USER " + username + " " + room;
        sendInstruction(instruction);
    }
   
    //Method to delete a user from a chatroom
    public static void deleteChatroomUser(String username, String room) {
        String instruction = "REMOVE_CHATROOM_USER " + username + " " + room;
        sendInstruction(instruction);
    }
   
    //Method to get a list of all users from a chatroom
    public static void getChatroomUsers(String room) {
        String instruction = "GET_CHATROOM_USERS " + room;
        sendInstruction(instruction);
    }
   
    //Method to get the chat history
    public static void getChatHistory(String room) {
        String instruction = "GET_CHAT_HISTORY " + room;
        sendInstruction(instruction);
    }
   
    // Method to send an instruction to the server
    private static void sendInstruction(String instruction) {
        try {
            out.println(instruction);  // Send message to server
            String response = in.readLine();  // Read the server's response
            System.out.println("Server response: " + response);  // Print response from the server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public static void pause() throws Exception {
        System.out.println("PRESS ENTER");
        while(System.in.read() != '\n');
    }
}