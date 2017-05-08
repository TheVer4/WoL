package io.github.thever4.wol;

import java.io.OutputStream;
import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

public class Waker {

	static ArrayList<String> broadcasts;
	private String magic;
	private int port;
	
	
	public Waker(String mac, int port) {
		magic = "FFFFFF";
		for (int i = 0; i < 16; i++) {
			magic += mac;
		}
		this.port = port;
		System.out.println(magic);
	}
	
	public static void getBroadcast() {
		broadcasts = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while(en.hasMoreElements()) {
				NetworkInterface ni = en.nextElement();
				List<InterfaceAddress> list = ni.getInterfaceAddresses();
				Iterator<InterfaceAddress> it = list.iterator();
				while(it.hasNext()) {
					InterfaceAddress ia = it.next();
					if (ia.getBroadcast() != null) {
						broadcasts.add(ia.getBroadcast().toString());
					}
				}
			}
		} catch (SocketException e) {
			JOptionPane.showMessageDialog(null, "Please check your network adapters!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
	
	@SuppressWarnings("resource")
	public void wake() {
		try {
			Iterator<String> it = broadcasts.iterator();
			while (it.hasNext()) {
				String bcast = it.next().substring(1);
				OutputStream os = new Socket(bcast, this.port).getOutputStream();
				os.write(magic.getBytes());
				os.flush();
			}
		}
		catch (Exception e) {
			
		}
	}
}
