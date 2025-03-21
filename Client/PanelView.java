package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.sql.Timestamp;
import javax.swing.*;

import Server.Chatroom;
import Server.Message;
import Server.User;


public class PanelView extends JPanel {
    
    private RMIClient rmiClient;
    private Chatroom chatroom;
    private File selectedFile = null;

    public PanelView(int w, int h, RMIClient c) throws Exception {
    
            this.rmiClient = c;
            setLocation(0, 0);
            setPreferredSize(new Dimension(w,h));
            JTextArea ta1 = new JTextArea("Chatroom null");
            
            add(ta1);
            setLayout(null);
    }   
    
    public PanelView(int w, int h, Chatroom chatroom, RMIClient c, User user) throws Exception {
        this.rmiClient = c;
        this.chatroom = chatroom;
        

            
        setLocation(0, 0);
        setPreferredSize(new Dimension(w,h));
       
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JTextArea headerBar = new JTextArea(chatroom.getName());
        headerBar.setPreferredSize(new Dimension(150, 20));
        
        headerBar.setEditable(false);
        headerBar.setBackground(Color.LIGHT_GRAY);
            
        ChatWindow chatPanel = new ChatWindow(rmiClient, user, chatroom);
        
        
        rmiClient.getChatroomController().setChatWindow(chatPanel);
        rmiClient.getChatroomController().setChatroom(chatroom);
        
        JScrollPane chatScroll = new JScrollPane(chatPanel);
        chatScroll.setPreferredSize(new Dimension(w - 20, h - 150));
        
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            
            
            
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        JPanel messagPanel = new JPanel();
        messagPanel.setBackground(Color.lightGray);
        messagPanel.setLayout(new FlowLayout());


        

        JButton uploadImage = new JButton("Upload Image");
        uploadImage.setBackground(Color.cyan);
        uploadImage.addActionListener(e -> {
            if(selectedFile == null){
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getName());
                    uploadImage.setBackground(Color.GREEN);
                    uploadImage.setText("Image Uploaded!");
                }
            }else{
                selectedFile = null;
                uploadImage.setBackground(Color.cyan);
                uploadImage.setText("Upload Image");
                
            }
            
           
        });
    


        
        JTextField MessageInput = new JTextField();
        MessageInput.setToolTipText("Type your message here");
        MessageInput.setPreferredSize(new Dimension(w - 200, 50));
        
        MessageInput.setFont(new Font("Arial",10,20));
        MessageInput.addActionListener(e->{
            if (MessageInput.getText().length() < 1) return;
            Timestamp time =  new Timestamp(System.currentTimeMillis());
            try {
                
                
                
                if(rmiClient.getClientLogic().inChatroom(user, chatroom)) {

                    String image = null;
                    if (selectedFile != null) {
                        rmiClient.getClientLogic().uploadImage(selectedFile);                     
                        image = selectedFile.getName();
                        selectedFile = null;
                        uploadImage.setBackground(Color.cyan);
                        uploadImage.setText("Upload Image");
                        
                    }
                                                            
                    Message o = new Message(c.getClientLogic().getUserID(user), chatroom.getRoomID(), time, MessageInput.getText(), image);
                    

                    
                    rmiClient.getClientLogic().createMessage(o);  

                    chatPanel.addOneMessage(o);  
                }

            } catch (Exception e1) {
                e1.printStackTrace();
                }
                    MessageInput.setText("");
        });


        add(headerBar);
        add(chatScroll);

        messagPanel.add(MessageInput);
        messagPanel.add(uploadImage);


        add(messagPanel);
        
        revalidate(); 
        repaint();
    }
}