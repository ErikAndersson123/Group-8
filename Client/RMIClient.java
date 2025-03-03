package Client;

import java.rmi.RemoteException;
import java.rmi.Naming;
import Server.Subject;


public class RMIClient {
    
    private Subject dataLogic;
    private ClientLogic clientLogic;
    
    private Observer userInfoController;
    private Observer chatroomController;
        
    
    public RMIClient() throws RemoteException{
        

        try {
            // Connect to the RMI server
            dataLogic = (Subject) Naming.lookup("rmi://192.168.32.3/Subject");
            clientLogic = new ClientLogic(dataLogic);
            
            System.out.println("Connected to RMI server.");

            // Set up observers
            userInfoController = new UserInfoController(this);
            chatroomController = new ChatroomController(this);
            dataLogic.addSubscriber(userInfoController);
            dataLogic.addSubscriber(chatroomController);
            System.out.println("Client is waiting for updates");

            // Pass RMI connection to ClientLogic
            ClientLogic clientLogic = new ClientLogic(dataLogic);
            //clientLogic.test();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ClientLogic getClientLogic() throws Exception {
        return this.clientLogic;
    }
    
    public Observer getUserInfoController() {
        return this.userInfoController;
    }
    
    public Observer getChatroomController() {
        return this.chatroomController;
    }
    
}