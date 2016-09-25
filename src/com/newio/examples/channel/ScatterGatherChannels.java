package com.newio.examples.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class ScatterGatherChannels {

	public static void main(String[] args) {
		try (FileInputStream fileInputStream = new FileInputStream("resources\\ScatteringFile.txt");
				FileOutputStream fileOutputStream = new FileOutputStream("resources\\GatheringFile.txt");
				ScatteringByteChannel scatChannel = (ScatteringByteChannel) Channels.newChannel(fileInputStream);
				GatheringByteChannel gatChannel = (GatheringByteChannel) Channels.newChannel(fileOutputStream)) {

			ByteBuffer byteBuffer1 = ByteBuffer.allocate(5);
			ByteBuffer byteBuffer2 = ByteBuffer.allocate(10);
			ByteBuffer[] byteBuffers = { byteBuffer1, byteBuffer2 };
			scatChannel.read(byteBuffers);
			System.out.println("Reading in single call : byteBuffer1");
			System.out.println(byteBuffer1.array());
			System.out.println("Reading in single call : byteBuffer2");
			System.out.println(byteBuffer2.array());
			System.out.println("Reading in loop : byteBuffer1");
			byteBuffer1.flip();
			while (byteBuffer1.hasRemaining()) {
				System.out.println((char) byteBuffer1.get());
			}
			System.out.println("Reading in loop : byteBuffer2");
			byteBuffer2.flip();
			while (byteBuffer2.hasRemaining()) {
				System.out.println((char) byteBuffer2.get());
			}

			byteBuffer1.clear();
			byteBuffer2.clear();

			gatChannel.write(byteBuffers);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
