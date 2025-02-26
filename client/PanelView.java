package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class PanelView extends JPanel implements View{
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
	public PanelView(int w, int h) throws Exception {
		ClientConnection cc = new ClientConnection();
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
			if (t1.getText().length() < 1)
				return;
			Long time = System.currentTimeMillis();
			System.out.println(time+" : "+ChatroomView.u.getUsername()+" : "+t1.getText()+" : "+ChatroomView.RoomID);
			cc.createMessage(ChatroomView.u.getUsername(), ChatroomView.RoomID, time, t1.getText());
			t1.setText("");
		});
		
		add(ta1);
		add(t1);
		setLayout(null);
	}
}
