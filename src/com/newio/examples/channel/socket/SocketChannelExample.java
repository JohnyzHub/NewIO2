package com.newio.examples.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class SocketChannelExample {

	public static void main(String[] args) {
		try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9999))) {
			socketChannel.configureBlocking(false);
			// InetSocketAddress inetAddress = new
			// InetSocketAddress("localhost", 9999);
			// socketChannel.connect(inetAddress);

			while (!socketChannel.finishConnect()) {
				System.out.print(".");
			}

			ByteBuffer byteBuffer = ByteBuffer.allocate(200);
			while (socketChannel.read(byteBuffer) > 0) {
				byteBuffer.flip();
				System.out.print(Charset.defaultCharset().decode(byteBuffer));
				byteBuffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
