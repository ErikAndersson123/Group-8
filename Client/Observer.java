package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import Server.Chatroom;

public interface Observer extends Remote {
    void update() throws RemoteException;
    void setChatroom(Chatroom chatroom) throws RemoteException;
    void setChatWindow(ChatWindow chatWindow) throws RemoteException;
    
    
    
}
