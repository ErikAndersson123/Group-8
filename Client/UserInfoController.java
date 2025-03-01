package Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserInfoController extends UnicastRemoteObject implements Observer {
    
    private static final long serialVersionUID = 1L;
    
    public UserInfoController() throws RemoteException {
        super();
    }
    
    @Override
    public void update() throws RemoteException {
        System.out.println("Received update");
    }
    
}