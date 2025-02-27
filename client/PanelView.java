package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.*;

import server.Chatroom;
import server.Message;

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
	public PanelView(int w, int h, String roomName) throws Exception {
		ClientConnection cc = new ClientConnection();
		setLocation(200, 0);
		setPreferredSize(new Dimension(w,h));
		JTextArea ta1 = new JTextArea("Chatroom "+ roomName);
		ta1.setBounds(getX()+w/2-50, 0, 100, 20);
		ta1.setEditable(false);
		ta1.setBackground(Color.LIGHT_GRAY);


		
		
        LinkedList<Message> messages = cc.getChatHistory(roomName);
        for (Message message : messages) {
            System.out.println(message);
        }
		
		ChatWindow chatPanel = new ChatWindow(messages);
        JScrollPane chatScroll = new JScrollPane(chatPanel);
        chatScroll.setBounds(200, 30, w - 20, h - 150);
        chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		JTextField t1 = new JTextField();
		t1.setToolTipText("Type your message here");
		t1.setBounds(getX()+10,h-100, w-50, 50);
		t1.setFont(new Font("Arial",10,20));
		t1.addActionListener(e->{
			if (t1.getText().length() < 1)
				return;
			
			System.out.println(ChatroomView.u.getUsername()+" : "+t1.getText()+" : "+roomName);
			cc.createMessage(ChatroomView.u.getUsername(), roomName, t1.getText());
			t1.setText("");
		});
		
		add(ta1);
		add(t1);
		add(chatScroll);
		setLayout(null);
	}
}
