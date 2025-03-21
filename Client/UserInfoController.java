package Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Server.Chatroom;

public class UserInfoController extends UnicastRemoteObject implements Observer {
    
    private static final long serialVersionUID = 1L;
    
    private RMIClient rmiClient;
    
    public UserInfoController(RMIClient rmiClient) throws RemoteException {
        super();
        this.rmiClient = rmiClient;
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
