package Client;

import java.rmi.Naming;
import Server.Subject;

public class RMIClient {
    
    public static void main(String[] args) {
        try {
            // Connect to the RMI server
            Subject dataLogic = (Subject) Naming.lookup("rmi://localhost/Subject");
            System.out.println("Connected to RMI server.");

            // Set up observers
            Observer userInfoController = new UserInfoController();
            Observer chatroomController = new ChatroomController();
            dataLogic.addSubscriber(userInfoController);
            dataLogic.removeSubscriber(chatroomController);
            System.out.println("Client is waiting for updates");

            // Pass RMI connection to ClientLogic
            ClientLogic clientLogic = new ClientLogic(dataLogic);
            clientLogic.test();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
