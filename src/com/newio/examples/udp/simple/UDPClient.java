package com.newio.examples.udp.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {

	public UDPClient() {
		System.out.println("Client Started");
		try (DatagramSocket client = new DatagramSocket(); Scanner scanner = new Scanner(System.in);) {
			DatagramPacket packet = null;
			String userMessage = null;
			String messageReceived = null;
			byte[] bytesSent = null;
			byte[] bytesReceived = null;
			InetAddress address = InetAddress.getByName("localhost");
			while (true) {
				System.out.println("\nEnter a message: ");
				userMessage = scanner.nextLine();
				if (userMessage.equalsIgnoreCase("EXIT") || userMessage.equalsIgnoreCase("QUIT")) {
					break;
				}
				bytesSent = userMessage.getBytes();
				packet = new DatagramPacket(bytesSent, bytesSent.length, address, 9009);
				client.send(packet);
				bytesReceived = new byte[1024];
				packet = new DatagramPacket(bytesReceived, bytesReceived.length);
				client.receive(packet);
				messageReceived = new String(packet.getData());
				System.out.println("Echo from server : " + messageReceived + " : " + packet.getAddress());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Client Terminated");
	}

	public static void main(String[] args) {
		new UDPClient();
	}

}
