package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.rmi.Naming;
import java.util.LinkedList;

import javax.swing.*;

import Server.Chatroom;
import Server.Message;
import Server.Subject;

public class PanelView extends JPanel {
    
    private RMIClient rmiClient;


    public PanelView(int w, int h, RMIClient rmiClient) throws Exception {
    
            this.rmiClient = rmiClient;
            setLocation(200, 0);
            setPreferredSize(new Dimension(w,h));
            JTextArea ta1 = new JTextArea("Chatroom null");
            
            add(ta1);
            setLayout(null);
    }
    
    public PanelView(int w, int h,int roomID, Chatroom r, RMIClient rmiClient) throws Exception {
            this.rmiClient = rmiClient;

            
            setLocation(200, 0);
            setPreferredSize(new Dimension(w,h));
            JTextArea ta1 = new JTextArea(r.getName());
            ta1.setBounds(getX()+w/2-50, 0, 100, 20);
            ta1.setEditable(false);
            ta1.setBackground(Color.LIGHT_GRAY);
            ChatWindow chatPanel = new ChatWindow(rmiClient.getClientLogic().getChatHistory(r));
            //chatPanel.setPreferredSize(new Dimension(w - 40, h - 200));
            
            chatPanel.setPreferredSize(new Dimension(400, 500));
            chatPanel.setMinimumSize(new Dimension(400, 500));
            chatPanel.setMaximumSize(new Dimension(400, 500));
 
            
            rmiClient.getChatroomController().setChatWindow(chatPanel);
            rmiClient.getChatroomController().setChatroom(r);
            
            

            
            
            JScrollPane chatScroll = new JScrollPane(chatPanel);
            
            chatScroll.setBounds(250, 30, w - 15 , h - 150);
            //chatScroll.setBounds(getX(), 0, getWidth() - 20, h - 150);
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        chatScroll.setPreferredSize(new Dimension(w - 260, h - 150));
        JTextField t1 = new JTextField();
        t1.setToolTipText("Type your message here");
        t1.setBounds(getX()+10,h-100, w-50, 50);
        t1.setFont(new Font("Arial",10,20));
        t1.addActionListener(e->{
            if (t1.getText().length() < 1) return;
            Long time = System.currentTimeMillis();
            //System.out.println(time+" : "+ChatroomView.u.getUsername()+" : "+t1.getText()+" : "+roomID);
            try {
                Message o = new Message(ChatroomView.u.getUserID(), roomID, time, t1.getText(), "");
                

                // Call the database interaction method (createMessage)
                rmiClient.getClientLogic().createMessage(o);  // This is where the database call happens

                // After the database operation, update the UI on the EDT
                chatPanel.addOneMessage(o);  // Update the chat window with the new message
                

                
                
                
                
                
                //rmiClient.getClientLogic().createMessage(o);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                }
                    t1.setText("");
        });
        add(chatScroll);
        add(ta1);
        add(t1);
        setLayout(null);
    }
}
