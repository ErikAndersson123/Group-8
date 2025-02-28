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
    ClientLogic cl;
    public static User u;
    private JList list;
    private int[] ch;
    private DefaultListModel listModel;
    private PanelView pv;
    public ChatroomView(User user,ClientLogic cl) throws Exception {
        u = user;
        this.cl = cl;
        Chatroom[] cr = cl.getUserChatrooms(user);
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
                pv = new PanelView(600,600, (int)list.getSelectedValue(),cr[list.getSelectedIndex()]);
                
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
        
        pv = new PanelView(600,600);
        
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
