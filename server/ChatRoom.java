package server;

import java.sql.SQLException;
import java.util.LinkedList;

public class ChatRoom {
	private int RoomID;
	private LinkedList<User> users;
	private LinkedList<Message> history;
	PortalConnection p = new PortalConnection();
	
	public ChatRoom(int RoomID) throws SQLException, ClassNotFoundException{
		this.RoomID = RoomID;
		p.Createroom(RoomID);
	}
	
	
	public int getRoomID() {
		return RoomID;
	}
	public void setRoomID(int roomID) {
		RoomID = roomID;
	}
	public LinkedList<User> getUsers() {
		return users;
	}
	public void addUser(User user) {
		this.users.add(user);
	}
	public void removeUser(User user) {
		this.users.remove(users.indexOf(user));
	}
	public LinkedList<Message> getHistory() {
		return history;
	}
	public void addMessage(Message msg) {
		this.history.add(msg);
	}
	public void removeMessage(Message msg) {
		this.history.remove(history.indexOf(msg));
	}
	
	
	
	
	
}
