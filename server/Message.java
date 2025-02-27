package server;

import java.sql.Timestamp;

public class Message {
    
    private int messageID;
    private int senderID;
    private int roomID;
    private Timestamp timestamp;
    private String text;
    
    public Message(int messageID, int senderID, int roomID, Timestamp timestamp, String text) {
        this.messageID = messageID;
        this.senderID = senderID;
        this.roomID = roomID;
        this.timestamp = timestamp;
        this.text = text;
    }
    
    public int getMessageID() {
        return messageID;
    }
    
    public int getSenderID() {
        return senderID;
    }
    
    public int getRoomID() {
        return roomID;
    }
    
    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }
    
    public String getText() {
        return text;
    }
    @Override
    public String toString() {
        return "Message{" +
            "messageID=" + messageID +
            ", senderID=" + senderID +
            ", roomID=" + roomID +
            ", timestamp=" + timestamp +
            ", text='" + text + '\'' +
            '}';
    }

}