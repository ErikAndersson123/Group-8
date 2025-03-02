package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.*;

import Server.Chatroom;
import Server.User;

public class ChatroomView extends JFrame {
    private static final long serialVersionUID = 1L;
    RMIClient rmiClient;
    public static User u;
    private JList list;
    private int[] ch;
    private DefaultListModel listModel;
    private PanelView pv;
    public ChatroomView(User user, RMIClient rmiClient) throws Exception {
        u = user;
        this.rmiClient = rmiClient;
        
        LinkedList<Chatroom> Chatrooms = rmiClient.getClientLogic().getUserChatrooms(user);
        
        Chatroom[] cr = new Chatroom[Chatrooms.size()];
        int index = 0;
        
        for (Chatroom chatroom : Chatrooms) {
            cr[index] = chatroom;
            index++;
        }
        
        //Chatroom[] cr = cl.getUserChatrooms(user);
        createArray(cr);
        setPreferredSize(new Dimension(800,600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel pa = new JPanel();
        pa.setSize(200,600);
        pa.setBackground(Color.LIGHT_GRAY);    
        listModel = new DefaultListModel();
        for (int i = 0 ; i < ch.length; i++) {
            listModel.addElement(ch[i]);
        }
        
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(e->{
            try {
                remove(pv);
                pv = new PanelView(600,600, (int)list.getSelectedValue(),cr[list.getSelectedIndex()], rmiClient);
                
                add(pv);
                invalidate();
                validate();
                repaint();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        list.setVisibleRowCount(8);
        list.setPreferredSize(new Dimension(100, 100));
        JScrollPane listscroll = new JScrollPane(list);
        
        pv = new PanelView(600,600, rmiClient);
        
        pa.add(listscroll, BorderLayout.CENTER);
        add(pa);
        add(pv);
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
