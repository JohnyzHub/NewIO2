package com.newio.examples.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class MappedByteBufferExample {
	public static void main(String[] args) {
		try (RandomAccessFile file = new RandomAccessFile("resources\\MappedByteBufferExample.txt", "rw");
				FileChannel fileChannel = file.getChannel();) {
			long size = fileChannel.size();
			MappedByteBuffer mappedBuffer = fileChannel.map(MapMode.READ_WRITE, 0, size);

			System.out.println("Text from File : -----");
			while (mappedBuffer.hasRemaining()) {
				System.out.print((char) mappedBuffer.get());
			}
			System.out.println("\n");

			int limit = mappedBuffer.limit();
			for (int index = 0; index < (limit / 2); index++) {
				byte b1 = mappedBuffer.get(index);
				byte b2 = mappedBuffer.get(limit - index - 1);
				mappedBuffer.put(index, b2);
				mappedBuffer.put(limit - index - 1, b1);
			}
			mappedBuffer.flip();
			System.out.println("Text Reversed : -----");
			while (mappedBuffer.hasRemaining()) {
				System.out.print((char) mappedBuffer.get());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}