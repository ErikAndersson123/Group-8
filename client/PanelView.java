package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.rmi.Naming;
import java.util.LinkedList;

import javax.swing.*;

import server.Chatroom;
import server.Message;
import server.Subject;

public class PanelView extends JPanel implements View{
	ClientLogic cl = new ClientLogic((Subject) Naming.lookup("rmi://localhost/Subject"));

public PanelView(int w, int h) throws Exception {
		
		setLocation(200, 0);
		setPreferredSize(new Dimension(w,h));
		JTextArea ta1 = new JTextArea("Chatroom null");
		
		add(ta1);
		setLayout(null);
	}
	
	public PanelView(int w, int h,int roomID,Chatroom r) throws Exception {
		
		setLocation(200, 0);
		setPreferredSize(new Dimension(w,h));
		JTextArea ta1 = new JTextArea("Chatroom "+roomID);
		ta1.setBounds(getX()+w/2-50, 0, 100, 20);
		ta1.setEditable(false);
		ta1.setBackground(Color.LIGHT_GRAY);
		JTextArea chat = new JTextArea();
		chat.setBounds(getX(), 0, getWidth(), getHeight()-75);
		printChat(chat,cl.getChatHistory(r));
		JTextField t1 = new JTextField();
		t1.setToolTipText("Type your message here");
		t1.setBounds(getX()+10,h-100, w-50, 50);
		t1.setFont(new Font("Arial",10,20));
		t1.addActionListener(e->{
			if (t1.getText().length() < 1)
				return;
			Long time = System.currentTimeMillis();
			System.out.println(time+" : "+ChatroomView.u.getUsername()+" : "+t1.getText()+" : "+roomID);
			try {
				cl.createMessage(new Message(ChatroomView.u.getUserID(), roomID, time, t1.getText(), null));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			t1.setText("");
		});
		
		add(ta1);
		add(t1);
		setLayout(null);
	}

	private void printChat(JTextArea chat, LinkedList<Message> chatHistory) {
		// TODO Auto-generated method stub
		for(int i = 0; i < chatHistory.size(); i++) {
			chat.setText(chat.getText()+"/n" +chatHistory);
		}
	}
}
