package Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Server.Chatroom;

public class UserInfoController extends UnicastRemoteObject implements Observer {
    
    private static final long serialVersionUID = 1L;
    
    
    public UserInfoController(RMIClient c) throws RemoteException {
        super();
        RMIClient rmiClient = c;
    }
    
    @Override
    public void update() throws RemoteException {
        System.out.println("Received update");
    }
    
    public void setChatroom(Chatroom chatroom){
    
    };
    
    public void setChatWindow(ChatWindow chatWindow){
        
    };
    
}