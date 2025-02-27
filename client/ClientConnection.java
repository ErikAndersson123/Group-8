package client;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import server.Chatroom;
import server.Message;

public class ClientConnection {
   
    private static final String SERVER_ADDRESS = "192.168.1.3";
    private static final int SERVER_PORT = 8080;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnection() {
        try {
            this.socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to server: " + SERVER_ADDRESS + ":" + SERVER_PORT);
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    // Method to create a new user
    public void createUser(String username, String password) {
        String instruction = "CREATE_USER " + username + " " + password;
        sendInstruction(instruction);
    }

    // Method to delete a user
    public void deleteUser(String username) {
        String instruction = "DELETE_USER " + username;
        sendInstruction(instruction);
    }

    // Method to authenticate a user
    public boolean authenticateUser(String username, String password)throws Exception {
        String instruction = "AUTHENTICATE_USER " + username + " " + password;
        String response = sendInstruction(instruction);
        return "User authenticated successfully.".equalsIgnoreCase(response);
    }
    
    

    // Method to create a chatroom
    public void createChatroom(String name) {
        String instruction = "CREATE_CHATROOM " + name;
        sendInstruction(instruction);
    }

    // Method to delete a chatroom
    public void deleteChatroom(String name) {
        String instruction = "DELETE_CHATROOM " + name;
        sendInstruction(instruction);
    }

    // Method to create a message in a chatroom
    public void createMessage(String username, String room, String text) {
        String instruction = "CREATE_MESSAGE " + username + " " + room  + " " + text;
        sendInstruction(instruction);
    }
   
    // Method to delete a message in a chatroom
    public void deleteMessage(int messageID, String room) {
        String instruction = "DELETE_MESSAGE " + messageID + " " + room;
        sendInstruction(instruction);
    }
   
    //Method to add a user to a chatroom
    public void addChatroomUser(String username, String room) {
        String instruction = "ADD_CHATROOM_USER " + username + " " + room;
        sendInstruction(instruction);
    }
   
    //Method to delete a user from a chatroom
    public void deleteChatroomUser(String username, String room) {
        String instruction = "REMOVE_CHATROOM_USER " + username + " " + room;
        sendInstruction(instruction);
    }
   
    //Method to get a list of all users from a chatroom
    public void getChatroomUsers(String room) {
        String instruction = "GET_CHATROOM_USERS " + room;
        sendInstruction(instruction);
    }
   
    //Method to get the chat history
    public LinkedList<Message> getChatHistory(String room) {
        String instruction = "GET_CHAT_HISTORY " + room;
        String response = sendInstruction(instruction);
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<Message>>() {}.getType();
        LinkedList<Message> messages = gson.fromJson(response, listType);

        return messages;
    }

    public LinkedList<Chatroom> getChatrooms(){
        String instruction = "GET_CHATROOMS ";
        String response = sendInstruction(instruction);
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<Chatroom>>() {}.getType();
        LinkedList<Chatroom> chatrooms = gson.fromJson(response, listType);

        return chatrooms;
    }
   
    // Method to send an instruction to the server
    private String sendInstruction(String instruction) {
        try {
            out.println(instruction);  // Send message to server
            String response = in.readLine();  // Read the server's response
            System.out.println("Server response: " + response);  // Print response from the server
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }
    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
    
    public void pause() throws Exception {
        System.out.println("PRESS ENTER");
        while(System.in.read() != '\n');
    }
    public void closeConnection() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
                System.out.println("Connection closed.");
            }
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
}