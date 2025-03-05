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
        
        
                        
        //this.chatroom.setChatHistory(rmiClient.getClientLogic().getChatHistory(chatroom));
        //this.chatroom.setChatroomUsers(rmiClient.getClientLogic().getChatroomUsers(chatroom));
        

            
        setLocation(0, 0);
        setPreferredSize(new Dimension(w,h));
       
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JTextArea ta1 = new JTextArea(chatroom.getName());
        ta1.setPreferredSize(new Dimension(150, 20));
        
        ta1.setEditable(false);
        ta1.setBackground(Color.LIGHT_GRAY);
            
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
    


        
        JTextField t1 = new JTextField();
        t1.setToolTipText("Type your message here");
        t1.setPreferredSize(new Dimension(w - 200, 50));
        
        t1.setFont(new Font("Arial",10,20));
        t1.addActionListener(e->{
            if (t1.getText().length() < 1) return;
            Timestamp time =  new Timestamp(System.currentTimeMillis());
            try {
                
                
                
                if(rmiClient.getClientLogic().inChatroom(user, chatroom)) {

                    String image = null;
                    if (selectedFile != null) {
                        rmiClient.getClientLogic().uploadImage(selectedFile);
                        //System.out.println("selectedFile is not null: " + selectedFile.getName());
                        image = selectedFile.getName();

                        //System.out.println(image);

                        selectedFile = null;
                        uploadImage.setBackground(Color.cyan);
                        uploadImage.setText("Upload Image");
                        
                    }
                                                            
                    Message o = new Message(ChatroomView.u.getUserID(), chatroom.getRoomID(), time, t1.getText(), image);
                    

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

        messagPanel.add(t1);
        messagPanel.add(uploadImage);


        add(messagPanel);
        
        
        
        //setLayout(new FlowLayout());
        revalidate(); // Refresh panel
        repaint();
    }
}