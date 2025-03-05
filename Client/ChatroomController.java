package Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.*;
import Server.Chatroom;


public class ChatroomController extends UnicastRemoteObject implements Observer {
    
    private static final long serialVersionUID = 1L;
    private RMIClient rmiClient;
    private ChatWindow chatWindow;
    private Chatroom chatroom;
    
    public ChatroomController(RMIClient rmiClient) throws RemoteException {
        super();
    }
    
    @Override
    public void update() throws RemoteException {
        

        

            System.out.println("Received update");
                
            
        
            SwingUtilities.invokeLater(() -> {
                try
                {
                chatWindow.ReloadChat();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                } 
            });
        
    }
    
    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }
    
    
    public void setChatWindow(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }
    
    
}