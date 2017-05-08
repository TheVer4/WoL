package io.github.thever4.wol;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		Waker.getBroadcast();
		Window w = new Window("WoL");
		w.setVisible(true);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setResizable(false);
		w.setSize(450, 100);
		w.setLocationRelativeTo(null);
	}
	
}
