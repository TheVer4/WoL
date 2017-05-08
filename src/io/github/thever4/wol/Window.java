package io.github.thever4.wol;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	JButton bwake, bclose;
	JTextField MAC, PORT = new JTextField(10);
	JLabel Mac, Port;
	
	eventListener l = new eventListener();
	
	public Window(String title) {
		super(title);
		setLayout(new GridLayout(3, 2));
		
		bwake = new JButton("Wake!");
		bclose = new JButton("Exit");
		
		bwake.addActionListener(l);
		bclose.addActionListener(l);
		
		Mac = new JLabel("Enter MAC without semicolon");
		Port = new JLabel("Port");
		
		MAC = new JTextField(10);
		PORT = new JTextField("9");
		
		add(Mac);
		add(MAC);
		add(Port);
		add(PORT);
		add(bwake);
		add(bclose);
		
				
	}
	
	private class eventListener implements ActionListener {

		@SuppressWarnings("resource")
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bwake) {
				if(!MAC.getText().toString().trim().equals("") && !PORT.getText().toString().trim().equals("")) {
					Waker w = new Waker(MAC.getText().toString().trim(), new Scanner(PORT.getText().toString().trim()).nextInt());
					w.wake();
				}
				else JOptionPane.showMessageDialog(null, "One of fields is empty or incorrect!", "Warning!", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if (e.getSource() == bclose) {
				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure want to exit?", "Confirm", JOptionPane.YES_NO_OPTION);
				switch(confirm) {
					case JOptionPane.YES_OPTION:
						System.exit(0);
						break;
					case JOptionPane.NO_OPTION:
						
						break;
				}
			}
			
		}
		
	}
	
}
