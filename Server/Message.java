package Server;

import java.io.Serializable;

public class Message implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int messageID;
    private int senderID;
    private int roomID;
    private long timestamp;
    private String text;
    private String image;
    
    public Message(int senderID, int roomID, long timestamp, String text, String image) {
        this.senderID = senderID;
        this.roomID = roomID;
        this.timestamp = timestamp;
        this.text = text;
        this.image = image;
    }
    
    public Message(int messageID, int senderID, int roomID, long timestamp, String text, String image) {
        this.messageID = messageID;
        this.senderID = senderID;
        this.roomID = roomID;
        this.timestamp = timestamp;
        this.text = text;
        this.image = image;
    }
    
    public int getMessageID() {
        return messageID;
    }
    
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }
    
    public int getSenderID() {
        return senderID;
    }
    
    public int getRoomID() {
        return roomID;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public String getText() {
        return text;
    }
    
    public String getImage() {
        return image;
    }
}
