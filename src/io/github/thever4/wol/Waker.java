package io.github.thever4.wol;

import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

public class Waker {

	static ArrayList<String> broadcasts;
	private byte[] magic;
	private ArrayList<Byte> magicb;
	private int port;
	
	
	public Waker(String mac, int port) {
		magicb = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			magicb.add(Byte.parseByte("f", 16));
		}
		for (int j = 0; j < 16; j++) {
			for (int i = 0; i < 12; i++) magicb.add(Byte.parseByte(Character.toString(mac.charAt(i)), 16));
		}
		magic = new byte[magicb.size()];
		for (int i = 0; i < magic.length; i++) {
			magic[i] = magicb.get(i).byteValue();
		}
		this.port = port;
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
	
	public void wake() {
		try {
			Iterator<String> it = broadcasts.iterator();
			while (it.hasNext()) {
				String bcast = it.next().substring(1);
				DatagramPacket packet = new DatagramPacket(magic, magic.length, InetAddress.getByName(bcast), this.port);
				DatagramSocket socket = new DatagramSocket();
				socket.send(packet);
				socket.close();
				System.out.println("Sending packages on [" + bcast + "]");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unknown error while sending Magic package!", "Warning!", JOptionPane.ERROR_MESSAGE);
		}
	}
}
