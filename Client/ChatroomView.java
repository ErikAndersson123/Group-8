package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

import Server.Chatroom;
import Server.User;
import Server.Message;

public class ChatroomView extends JFrame {
    private static final long serialVersionUID = 1L;
    private RMIClient rmiClient;
    private static User u;
    private JList<String> list;
    private int[] ch;
    private DefaultListModel<String> listModel;
    private PanelView pv;
    private String currentRID;

    public Chatroom selected = null;
    public ChatroomView(User user, RMIClient rmiClient) throws Exception {
        u = user;
        this.rmiClient = rmiClient;
        currentRID = "";
        
        LinkedList<Chatroom> Chatrooms = rmiClient.getClientLogic().getChatrooms();
        
        Chatroom[] cr = new Chatroom[Chatrooms.size()];
        int index = 0;
        listModel = new DefaultListModel<>();

        for (Chatroom chatroom : Chatrooms) {
            cr[index] = chatroom;
            index++;
            listModel.addElement(chatroom.getName());
        }
        
        createArray(cr);
        setPreferredSize(new Dimension(800,600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JPanel pa = new JPanel();
        pa.setPreferredSize(new Dimension(200, 600));
        pa.setBackground(Color.LIGHT_GRAY);    
        
        
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        


        
        
        
        
        list.addListSelectionListener(e->{
            if (currentRID.equals((String) list.getSelectedValue())) {
                return;
            }
            try {
                String ChatroomName = (String) list.getSelectedValue();
                currentRID = ChatroomName;
                System.out.println(ChatroomName);
                for (Chatroom chatroom : Chatrooms) {
                    if (chatroom.getName().equals(ChatroomName)){
                        selected = chatroom;
                        System.out.println("Selected " + selected.getName());
                        break;
                    }
                }
                remove(pv);
                pv = new PanelView(600,600, selected, rmiClient, user);
                
                add(pv);
                invalidate();
                validate();
                repaint();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        
        
        list.setVisibleRowCount(8);
        list.setPreferredSize(new Dimension(100, 100));
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(200, 100));




        pv = new PanelView(600,600, rmiClient);

        // Create Leave Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
       
        JButton joinButton = new JButton("Join Chatroom");
        JButton leaveButton = new JButton("Leave Chatroom");
        JButton createButton = new JButton("Create Chatroom");
        
        joinButton.setMaximumSize(new Dimension(200, 40));
        leaveButton.setMaximumSize(new Dimension(200, 40));
        createButton.setMaximumSize(new Dimension(200, 40));
        
        createButton.setBackground(Color.RED);
        createButton.setForeground(Color.WHITE);

        joinButton.setBackground(Color.RED);
        joinButton.setForeground(Color.WHITE);

        leaveButton.setBackground(Color.RED);
        leaveButton.setForeground(Color.WHITE);

        buttonPanel.add(joinButton);
        buttonPanel.add(createButton);
        buttonPanel.add(leaveButton);

        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    null, "Are you sure you want to leave the chatroom?", "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        rmiClient.getClientLogic().removeChatroomUser(u, selected);
                        JOptionPane.showMessageDialog(null, "You have left the chatroom.");
                        remove(pv); 
                        
                        invalidate();
                        validate();
                        repaint();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error leaving the chatroom.");
                    }
                }
            }
        });

        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    null, "Do you want to join this chatroom?", "Confirm Join",
                    JOptionPane.YES_NO_OPTION
                );
        
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        rmiClient.getClientLogic().addChatroomUser(u, selected);
                        JOptionPane.showMessageDialog(null, "Chat joined successfully!");
                        //remove(pv);
                        invalidate();
                        revalidate();
                        repaint();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error joining the chatroom.");
                    }
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("New Chatroom");

                if (name != null && !name.trim().isEmpty()) { // Prevent empty chatroom names
                try {
                    Chatroom chat = new Chatroom(name);
                    rmiClient.getClientLogic().createChatroom(chat);
                    chat.setRoomID(rmiClient.getClientLogic().getRoomID(chat));
    
                    listModel.addElement(chat.getName());
    
                    JOptionPane.showMessageDialog(null, "Chatroom Created Successfully!");



                    rmiClient.getClientLogic().addChatroomUser(u, chat);

 
                    selected = chat;
                    currentRID = chat.getName();
                    System.out.println("Switched to newly created chatroom: " + selected.getName());

                    if (pv != null) {
                        remove(pv);
                    }

                    pv = new PanelView(600, 600, selected, rmiClient, u);
                    add(pv, BorderLayout.CENTER);

                    list.setSelectedValue(chat.getName(), true);

                    revalidate();
                    repaint();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error creating the chatroom.");
                    }
                }
            }
        });

        setLayout(new BorderLayout());

        pa.setLayout(new BorderLayout());
        pa.add(listScroll, BorderLayout.CENTER);
        pa.add(buttonPanel, BorderLayout.SOUTH);

        add(pa, BorderLayout.WEST);  
        add(pv, BorderLayout.CENTER); 

        pack();
        setVisible(true);



        
        
        
    }   
    
    
    private void createArray(Chatroom[] a) {

        ch = new int[a.length];
        System.out.println(a.length);
        for (int i = 0; i < ch.length; i++) {
            ch[i] = a[i].getRoomID();
        }
    }
}

