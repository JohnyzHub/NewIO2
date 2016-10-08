package com.newio.examples.channel;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MappedByteBufferExample {
	public static void main(String[] args) {

		/**
		 * Alternate way of opening filechannel : RandomAccessFile file = new
		 * RandomAccessFile("resources\\MappedByteBufferExample.txt", "rw");
		 * FileChannel fileChannel = file.getChannel();
		 */

		Path filePath = Paths.get("resources\\MappedByteBufferExample.txt");
		try (FileChannel fileChannel = FileChannel.open(filePath, StandardOpenOption.READ, StandardOpenOption.WRITE);) {
			long size = fileChannel.size();
			MappedByteBuffer mappedBuffer = fileChannel.map(MapMode.READ_WRITE, 0, size);

			System.out.println("Text from File : -----");
			/**
			 * while (mappedBuffer.hasRemaining()) { System.out.print((char)
			 * mappedBuffer.get()); }
			 */

			System.out.append(Charset.defaultCharset().decode(mappedBuffer)).println("\n");
			;
			System.out.println("\n");

			int maxNumber = (int) size - 1;
			for (int index = 0; index < (maxNumber / 2); index++) {
				byte b1 = mappedBuffer.get(index);
				byte b2 = mappedBuffer.get(maxNumber - index);
				mappedBuffer.put(index, b2);
				mappedBuffer.put(maxNumber - index, b1);
			}
			mappedBuffer.flip();
			System.out.println("Text Reversed : -----");
			System.out.append(Charset.defaultCharset().decode(mappedBuffer)).println("\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}