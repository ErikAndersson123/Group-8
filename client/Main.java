package client;

import java.util.LinkedList;

import server.Chatroom;
import server.Message;
import server.User;

public class Main {

    private static ClientConnection c;
    
    private LinkedList<User> users = new LinkedList<>();
    private LinkedList<Chatroom> chatrooms = new LinkedList<>();

    public static void main(String[] args) throws Exception{
        c = new ClientConnection();
        test2();
        







    }
    
    public static void pause() throws Exception {
            System.out.println("PRESS ENTER");
            while(System.in.read() != '\n');
    }




    public static void test2()throws Exception{

        LinkedList<Message> messages = c.getChatHistory("General");
        for (Message message : messages) {
            System.out.println(message);
        }
        pause();
        LinkedList<Chatroom> chatrooms = c.getChatrooms();
        for (Chatroom chatroom : chatrooms) {
            System.out.println("Name: " + chatroom.getName() + " ChatroomID: " + chatroom.getRoomID());
        }
        pause();
        c.closeConnection();
    }

    public static void test3()throws Exception{
        c.createUser("Pelle", "123");
        c.createUser("Kalle", "123");
        
        c.createChatroom("Chat6");

        c.addChatroomUser("Pelle", "Chat6");
        c.addChatroomUser("Kalle", "Chat6");


        c.createMessage("Pelle", "Chat6", "Hej");
        c.createMessage("Kalle", "Chat6", "Hej");

        c.getChatroomUsers("Chat6");
        c.getChatHistory("Chat6");

        pause();
        c.closeConnection();
    }
    
    public static void test()throws Exception{
    
    if(c.authenticateUser("william", "5412")){
        System.out.println("yay");
    }
    
    pause();

    c.deleteUser("Pelle");
    c.deleteUser("Kalle");

    pause();

    c.deleteChatroom("Chat6");

    c.getChatroomUsers("Chat6");
    c.getChatHistory("Chat6");

    c.closeConnection(); // Close the connection at the end
    }

}    
