package Server;

import Client.Observer;
import Client.UserInfoController;
import Client.ChatroomController;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    
    public static void main(String[] args) {
        try {
            DataLogic dataLogic = new DataLogic();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/Subject", dataLogic);
            System.out.println("Bound objects: " + java.util.Arrays.toString(java.rmi.registry.LocateRegistry.getRegistry(1099).list()));
            System.out.println("Server is ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
