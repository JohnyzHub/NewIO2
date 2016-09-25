package com.newio.examples.udp.simple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

	public UDPServer() {
		System.out.println("Server Started");
		byte[] bytesReceived = null;
		DatagramPacket packet = null;
		try (DatagramSocket serverSocket = new DatagramSocket(9009);) {
			while (true) {
				bytesReceived = new byte[1024];
				packet = new DatagramPacket(bytesReceived, bytesReceived.length);
				serverSocket.receive(packet);
				String messageReceived = new String(packet.getData());
				System.out.println("Received : " + messageReceived + " : " + packet.getAddress());
				bytesReceived = messageReceived.getBytes();
				packet = new DatagramPacket(bytesReceived, bytesReceived.length, packet.getAddress(), packet.getPort());
				serverSocket.send(packet);
				System.out.println("Sent : " + messageReceived);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server Terminated");
	}

	public static void main(String[] args) {
		new UDPServer();
	}
}