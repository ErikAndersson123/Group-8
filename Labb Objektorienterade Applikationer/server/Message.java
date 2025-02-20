package server;


public class Message {
    private String senderID;
    private int roomID;
    private String timestamp;
    private String text;
    private String image;
    
    public Message(String senderID, int roomID, String timestamp, String text, String image) {
        this.senderID = senderID;
        this.roomID = roomID;
        this.timestamp = timestamp;
        this.text = text;
        this.image = image;
    }
    
    public String getSenderID() {
        return senderID;
    }
    
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
    
    public int getRoomID() {
        return roomID;
    }
    
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp() {
        this.timestamp = timestamp;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage() {
        this.image = image;
    }
}