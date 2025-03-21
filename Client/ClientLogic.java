package Client;

import Server.Subject;
import Server.Chatroom;
import Server.User;
import Server.Message;
import java.util.LinkedList;
import java.io.File;
import java.sql.Timestamp;

public class ClientLogic {
    
    private Subject dataLogic;
    
    public ClientLogic(Subject dataLogic) {
        this.dataLogic = dataLogic;
    }

    public void test() throws Exception {
        
        User user1 = new User("Pelle", "123");
        User user2 = new User("Kalle", "123");
        
        createUser(user1);
        createUser(user2);

        authenticateUser(user1);
        
        Chatroom chatroom = new Chatroom("Chat6");

        createChatroom(chatroom);

        addChatroomUser(user1, chatroom);
        addChatroomUser(user2, chatroom);

        createMessage(new Message(user1.getUserID(), chatroom.getRoomID(), getCurrentTimestamp(), "Hej", null));
        createMessage(new Message(user2.getUserID(), chatroom.getRoomID(), getCurrentTimestamp(), "Hej", null));

        pause();

        getChatroomUsers(chatroom);
        getUserChatrooms(user1);
        getChatHistory(chatroom);

        pause();

        deleteUser(user1);
        deleteUser(user2);

        pause();

        deleteChatroom(chatroom);
    }

    public void createUser(User user) throws Exception {
        dataLogic.createUser(user);
    }

    public void deleteUser(User user) throws Exception {
        dataLogic.deleteUser(user);
    }

    public boolean authenticateUser(User user) throws Exception {
        return dataLogic.authenticateUser(user);
    }

    public void createChatroom(Chatroom chatroom) throws Exception {
        dataLogic.createChatroom(chatroom);
    }

    public void deleteChatroom(Chatroom chatroom) throws Exception {
        dataLogic.deleteChatroom(chatroom);
    }

    public void createMessage(Message message) throws Exception {
        dataLogic.createMessage(message);
    }

    public void deleteMessage(Message message) throws Exception {
        dataLogic.deleteMessage(message);
    }

    public void addChatroomUser(User user, Chatroom chatroom) throws Exception {
        dataLogic.addChatroomUser(user, chatroom);
    }

    public void removeChatroomUser(User user, Chatroom chatroom) throws Exception {
        dataLogic.removeChatroomUser(user, chatroom);
    }

    public LinkedList<Message> getChatHistory(Chatroom chatroom) throws Exception {
        return dataLogic.chatHistory(chatroom);
    }
    
    public String messageInfo(Message message) throws Exception {
        return dataLogic.messageInfo(message);
    }

    public LinkedList<User> getChatroomUsers(Chatroom chatroom) throws Exception {
        return dataLogic.chatroomUsers(chatroom);
    }

    public LinkedList<Chatroom> getUserChatrooms(User user) throws Exception {
        return dataLogic.userChatrooms(user);
    }
    
    public LinkedList<User> getUsers() throws Exception {
        return dataLogic.getUsers();
    }
    
    public LinkedList<Chatroom> getChatrooms() throws Exception {
        return dataLogic.getChatrooms();
    }
    
    public int getUserID (User user) throws Exception {
        return dataLogic.getUserID(user);
    }
    
    public int getRoomID(Chatroom chatroom) throws Exception {
        return dataLogic.getRoomID(chatroom);
    }
    
    public boolean inChatroom(User user, Chatroom chatroom) throws Exception {
        return dataLogic.inChatroom(user, chatroom);
    }

    public byte[] getImageFile(Message msg) throws Exception{
        return dataLogic.getImageFile(msg);
    }

    public void uploadImage(File image) throws Exception{
        dataLogic.uploadImage(image);
    }

    public void pause() throws Exception {
        System.out.println("PRESS ENTER");
        while (System.in.read() != '\n');
    }
    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}