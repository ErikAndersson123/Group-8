package Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import javax.swing.*;
import Server.Chatroom;
import Server.Message;

public class ChatroomController extends UnicastRemoteObject implements Observer {
    
    private static final long serialVersionUID = 1L;
    private RMIClient rmiClient;
    private ChatWindow chatWindow;
    private Chatroom chatroom;
    
    public ChatroomController(RMIClient rmiClient) throws RemoteException {
        super();
        this.rmiClient = rmiClient;
    }
    
    @Override
    public void update() throws RemoteException {
        

        

            System.out.println("Received update");
                
                //LinkedList<Message> chatHistory = rmiClient.getClientLogic().getChatHistory(chatroom);
        
            SwingUtilities.invokeLater(() -> {
                try
                {
                chatWindow.ReloadChat();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                } // This updates the UI
            });
        
    }
    
    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }
    
    public void setChatWindow(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }
    
}