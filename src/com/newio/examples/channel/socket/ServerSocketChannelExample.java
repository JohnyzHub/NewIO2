package com.newio.examples.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelExample {

	public static void main(String[] args) {
		try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
			serverChannel.configureBlocking(false);
			serverChannel.bind(new InetSocketAddress(9999));
			String message = "Local Address: " + serverChannel.getLocalAddress();
			ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
			while (true) {
				System.out.print(".");
				try (SocketChannel socketChannel = serverChannel.accept()) {
					if (socketChannel != null) {
						System.out.println(
								"\nReceived Connection from:" + socketChannel.socket().getRemoteSocketAddress());
						byteBuffer.rewind();
						socketChannel.write(byteBuffer);
						break;
					} else {
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
