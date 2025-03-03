package Client;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Font;

import java.sql.Timestamp;
import javax.swing.*;

import Server.Chatroom;
import Server.Message;
import Server.User;


public class PanelView extends JPanel {
    
    //private RMIClient rmiClient;


    public PanelView(int w, int h, RMIClient c) throws Exception {
    
            //RMIClient rmiClient = c;
            setLocation(0, 0);
            setPreferredSize(new Dimension(w,h));
            JTextArea ta1 = new JTextArea("Chatroom null");
            
            add(ta1);
            setLayout(null);
    }
    
    public PanelView(int w, int h, Chatroom chatroom, RMIClient c, User user) throws Exception {
        RMIClient rmiClient = c;

            
        setLocation(0, 0);
        setPreferredSize(new Dimension(w,h));
       
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JTextArea ta1 = new JTextArea(chatroom.getName());
        ta1.setPreferredSize(new Dimension(150, 20));
        
        ta1.setEditable(false);
        ta1.setBackground(Color.LIGHT_GRAY);
            
        ChatWindow chatPanel = new ChatWindow(rmiClient, user, chatroom);
        
        
        c.getChatroomController().setChatWindow(chatPanel);
        c.getChatroomController().setChatroom(chatroom);
        
        JScrollPane chatScroll = new JScrollPane(chatPanel);
        chatScroll.setPreferredSize(new Dimension(w - 20, h - 150));
        
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            
            
            
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JTextField t1 = new JTextField();
        t1.setToolTipText("Type your message here");
        t1.setPreferredSize(new Dimension(w - 50, 50));
        
        t1.setFont(new Font("Arial",10,20));
        t1.addActionListener(e->{
            if (t1.getText().length() < 1) return;
            Timestamp time =  new Timestamp(System.currentTimeMillis());
            try {
                
                
                if(rmiClient.getClientLogic().inChatroom(user, chatroom)) {
                                                            
                    Message o = new Message(ChatroomView.u.getUserID(), chatroom.getRoomID(), time, t1.getText(), null);
                

                    // Call the database interaction method (createMessage)
                    rmiClient.getClientLogic().createMessage(o);  // This is where the database call happens

                    // After the database operation, update the UI on the EDT
                    chatPanel.addOneMessage(o);  // Update the chat window with the new message
                }

            } catch (Exception e1) {
                e1.printStackTrace();
                }
                    t1.setText("");
        });



        
        
        





        add(ta1);
        add(chatScroll);
        
        add(t1);
        //setLayout(new FlowLayout());
        revalidate(); // Refresh panel
        repaint();
    }
}