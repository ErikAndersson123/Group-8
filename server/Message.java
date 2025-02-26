package server;


public class Message {
    
    private int messageID;
    private int senderID;
    private int roomID;
    private String timestamp;
    private String text;
    private String image;
    
    public Message(int messageID, int senderID, int roomID, String timestamp, String text, String image) {
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
    
    public int getSenderID() {
        return senderID;
    }
    
    public int getRoomID() {
        return roomID;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public String getText() {
        return text;
    }
    
    public String getImage() {
        return image;
    }
}