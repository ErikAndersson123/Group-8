package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import javax.swing.*;

public class PanelView extends JPanel implements View{
	ClientController cc = new ClientController();
	
	
	
	public PanelView() {
		
		JTextField t1 = new JTextField();
		JTextArea ta1 = new JTextArea("Chatroom #43526");
		ta1.setBounds(350, 0, 200, 20);
		ta1.setEditable(false);
		ta1.setBackground(Color.LIGHT_GRAY);
		t1.setBounds(350, 500, 200, 20);
		add(ta1);
		add(t1);
		setLayout(null);
		
		
		
		
	}
	public PanelView(int w, int h) {
		setLocation(200, 0);
		setPreferredSize(new Dimension(w,h));
		JTextArea ta1 = new JTextArea("Chatroom #43526");
		ta1.setBounds(getX()+w/2-50, 0, 100, 20);
		ta1.setEditable(false);
		ta1.setBackground(Color.LIGHT_GRAY);
		JTextField t1 = new JTextField();
		t1.setToolTipText("Type your message here");
		t1.setBounds(getX()+10,h-100, w-50, 50);
		t1.setFont(new Font("Arial",10,20));
		t1.addActionListener(e->{
			Long time = System.currentTimeMillis();
			try {
				System.out.println(time+" : "+ChatroomView.u.getName()+" : "+t1.getText()+" : "+ChatroomView.RoomID);
				cc.Sendmsg(time,ChatroomView.u.getName(),"",t1.getText(),ChatroomView.RoomID);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		add(ta1);
		add(t1);
		setLayout(null);
		
		
		
		
	}
	
}
