package Server;

import java.util.*;

public class ChatroomHandler {
    
    private DatabaseHandler databaseHandler;
    
    private LinkedList<Chatroom> chatrooms = new LinkedList<>();
    
    public ChatroomHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        loadChatroomsFromDatabase();
        loadMessagesFromDatabase();
        loadChatroomUsersFromDatabase();
    }
    
    public void deleteUserFromChatrooms(User user) {
        for (Chatroom chatroom : chatrooms) {
            chatroom.removeChatroomUser(user);
        }
    }
    
    public void createChatroom(Chatroom chatroom) {
        chatroom.setRoomID(databaseHandler.nextAvailableRoomID());
        databaseHandler.registerChatroom(chatroom);
        chatrooms.add(chatroom);
        System.out.println("Chatroom created");
    }
    
    public void deleteChatroom(Chatroom chatroom) {
        databaseHandler.unregisterChatroom(chatroom);
        chatrooms.remove(chatroom);
        System.out.println("Chatroom deleted");
    }
    
    public void createMessage(Message message) {
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == message.getRoomID()) {
                message.setMessageID(databaseHandler.nextAvailableMessageID(c));
                databaseHandler.addMessage(message);
                c.addMessage(message);
            }
        }
        System.out.println("Message created");
    }
    
    public void deleteMessage(Message message) {
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == message.getRoomID()) {
                databaseHandler.removeMessage(message);
                c.removeMessage(message);
            }
        }
        System.out.println("Message deleted");
    }
    
    public void addChatroomUser(User user, Chatroom chatroom) {
        databaseHandler.registerChatroomUser(user, chatroom);
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                
                c.setChatroomUsers(databaseHandler.getAllChatroomUsers(chatroom));
            }
        }
        System.out.println("User added to chatroom");
    }
    
    public void removeChatroomUser(User user, Chatroom chatroom) {
        databaseHandler.unregisterChatroomUser(user, chatroom);
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                
                
                c.setChatroomUsers(databaseHandler.getAllChatroomUsers(chatroom));
            }
        }
        System.out.println("User removed from chatroom");
    }
    
    public LinkedList<Message> chatHistory(Chatroom chatroom) {
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                System.out.println("Chat history sent");
                return c.getChatHistory();
            }
        }
        System.out.println("Chat history sent");
        return null;
    }
    
    public LinkedList<User> chatroomUsers(Chatroom chatroom) {
        for (Chatroom c : chatrooms) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                System.out.println("Chatroom Users sent");
                return c.getChatroomUsers();
            }
        }
        System.out.println("Chatroom Users sent");
        return null;
    }
    
    public LinkedList<Chatroom> userChatrooms(User user) {
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
    
    public boolean inChatroom(User user, Chatroom chatroom) {
        for (Chatroom c : userChatrooms(user)) {
            if (c.getRoomID() == chatroom.getRoomID()) {
                return true;
            }
        }
        return false;
    }

    public void loadChatroomsFromDatabase() {
        chatrooms.clear();
        chatrooms.addAll(databaseHandler.getAllChatrooms());
    }
    
    public void loadMessagesFromDatabase() {
        for (Chatroom chatroom : chatrooms) {
            LinkedList<Message> messages = databaseHandler.getAllMessages(chatroom);
            chatroom.setChatHistory(messages);
        }
    }
    
    public void loadChatroomUsersFromDatabase() {
        for (Chatroom chatroom : chatrooms) {
            LinkedList<User> chatroomUsers = databaseHandler.getAllChatroomUsers(chatroom);
            chatroom.setChatroomUsers(chatroomUsers);
        }
    }
}