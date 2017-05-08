package io.github.thever4.wol;

import java.net.*;
import java.util.*;

import javax.swing.JOptionPane;

public class Waker {

	static ArrayList<String> broadcasts;
	private byte[] magic;
	private int port;
	
	
	public Waker(String mac, int port) {
		byte[] macb = getMacBytes(mac);
		magic = new byte[6 * 17];
		this.port = port;
		for (int i = 0; i < 6; i++) {
			magic[i] = (byte) 0xff;
		}
		for (int i = 6; i < magic.length; i += macb.length) {
			System.arraycopy(macb, 0, magic, i, macb.length);
		}
	}
	
	private byte[] getMacBytes(String mac) {
		byte[] bytes = new byte[6];
		String[] hexademicalArr = mac.split("(:|-)");
		if (hexademicalArr.length == 6) {
			try {
				for(int i = 0; i < 6; i++) {
					bytes[i] = (byte) Integer.parseInt(hexademicalArr[i], 16);
				}
			}
			catch (Exception e) {}
		}
		return bytes;
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
				JOptionPane.showMessageDialog(null, "Package sent on [" + bcast + "]", "Success!", JOptionPane.PLAIN_MESSAGE);
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unknown error while sending Magic package!", "Warning!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
