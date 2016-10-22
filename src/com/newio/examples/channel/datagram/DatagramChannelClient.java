package com.newio.examples.channel.datagram;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelClient {

	public static void main(String[] args) {

		ByteBuffer choice = ByteBuffer.wrap("MSFT".getBytes());
		ByteBuffer response = ByteBuffer.allocate(16);
		try (DatagramChannel clientChannel = DatagramChannel.open()) {
			clientChannel.send(choice, new InetSocketAddress("localhost", 9999));
			SocketAddress serverAddress = clientChannel.receive(response);
			System.out.println("Response received from : " + serverAddress);

			System.out.println("Open Price : " + response.getFloat(0));
			System.out.println("Low Price : " + response.getFloat(4));
			System.out.println("High Price : " + response.getFloat(8));
			System.out.println("Close Price : " + response.getFloat(12));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}