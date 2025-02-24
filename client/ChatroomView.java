package client;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import server.User;

public class ChatroomView extends JFrame implements View{
	public static int RoomID;
	public static User u;
	public ChatroomView(User user) {
		u = user;
		RoomID = 1;
		setPreferredSize(new Dimension(800,600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel pa = new JPanel();
		pa.setSize(200,600);
		pa.setBackground(Color.LIGHT_GRAY);
		
		PanelView pv = new PanelView(600,600);
		
		add(pa);
		add(pv);
		System.out.println(pv.getLocation());
		pack();
		setVisible(true);
		
		
		
	}
	
	
}
