package com.newio.examples.channel.datagram;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramChannelServer {

	public static void main(String[] args) {
		try (DatagramChannel datagramServer = DatagramChannel.open();
				DatagramSocket datagramSocket = datagramServer.socket();) {
			datagramSocket.bind(new InetSocketAddress("localhost", 9999));
			ByteBuffer symbol = ByteBuffer.allocate(4);
			ByteBuffer payLoad = ByteBuffer.allocate(16);

			System.out.println("Server starting and listening on port " + 9999 + " for incoming requests...");

			while (true) {
				symbol.clear();
				payLoad.clear();

				SocketAddress socketAddress = datagramServer.receive(symbol);
				if (socketAddress == null)
					return;

				System.out.println("Socket Address : " + socketAddress);

				String socketSymbol = new String(symbol.array());
				if (socketSymbol.equalsIgnoreCase("MSFT")) {
					payLoad.putFloat(0, 37.40f);
					payLoad.putFloat(4, 37.22f);
					payLoad.putFloat(8, 37.48f);
					payLoad.putFloat(12, 37.41f);
				} else {
					payLoad.putFloat(0, 0.0f);
					payLoad.putFloat(4, 0.0f);
					payLoad.putFloat(8, 0.0f);
					payLoad.putFloat(12, 0.0f);
				}
				datagramServer.send(payLoad, socketAddress);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}