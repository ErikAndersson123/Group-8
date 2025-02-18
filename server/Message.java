package server;

import java.sql.SQLException;
import java.sql.Time;

public class Message {
	private String SenderID;
	private int RoomID;
	private Time timestamp;
	private String text;
	private String image;
	PortalConnection p = new PortalConnection();
	public String getSenderID() {
		return SenderID;
	}
	public void setSenderID(String senderID) {
		SenderID = senderID;
	}
	public int getRoomID() {
		return RoomID;
	}
	public void setRoomID(int roomID) {
		RoomID = roomID;
	}
	public Time getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Time timestamp) {
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
	public void setImage(String image) {
		this.image = image;
	}
	public Message(String senderID, int roomID,Time time, String txt, String img)throws SQLException, ClassNotFoundException {
		SenderID = senderID;
		RoomID = roomID;
		timestamp = time;
		text = txt;
		image = img;
		p.sendMessage(senderID, roomID, time, txt, image);
	}
	public Message(String senderID, int roomID,Time time, String txt) throws SQLException, ClassNotFoundException{
		SenderID = senderID;
		RoomID = roomID;
		timestamp = time;
		text = txt;
		image = null;
		p.sendMessage(senderID, roomID, time, txt, image);
	}
}
