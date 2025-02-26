package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import server.User;

public class ChatroomView extends JFrame implements View{
	private static final long serialVersionUID = 1L;
	public static User u;
	private JList list;
	private int[] ch = {1,2,8,12,62,4,82,3124};
	private DefaultListModel listModel;
	private PanelView pv;
	public ChatroomView(User user) throws Exception {
		u = user;
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
				pv = new PanelView(600,600,(int)list.getSelectedValue());
				
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
	    
		pv = new PanelView(600,600,12);
		
		pa.add(listscroll, BorderLayout.CENTER);
		add(pa);
		add(pv);
		pack();
		setVisible(true);
		
		
		
	}
	
	
}
