package com.newio.examples.channel;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BufferExample {

	private static final String location = "resources\\ByteBufferExample.txt";

	public static void main(String[] args) {
		BufferExample bufferExample = new BufferExample();
		try {
			bufferExample.write();
			bufferExample.read();
			bufferExample.verifyPermissions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void write() throws IOException {
		Path path = Paths.get(location);
		File file = path.toFile();
		if (!file.canWrite()) {
			System.out.println("File - " + file.getName() + " is READ-ONLY");
			file.setWritable(true);
			System.out.println("File - " + file.getName() + " permission changed to WRITABLE");
		}

		byte[] inputBytes = new String("This is a sample text for testing ByteBuffer.").getBytes();
		ByteBuffer byteBuffer = ByteBuffer.wrap(inputBytes);

		try (FileChannel channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE);) {
			channel.write(byteBuffer);
		}
		System.out.println("Writing to File Finished");
	}

	private void read() throws IOException {
		Path path = Paths.get(location);
		ByteBuffer byteBuffer = ByteBuffer.allocate(5);
		try (FileChannel channel = FileChannel.open(path)) {
			while ((channel.read(byteBuffer)) > 0) {
				// No need to flip buffer if reading all bytes at once as an
				// array.
				System.out.print(new String(byteBuffer.array()));
				// Flip buffer if reading one byte each at a time, as below
				// byteBuffer.flip();
				// while (byteBuffer.hasRemaining()) {
				// System.out.print((char) byteBuffer.get());
				// }
				byteBuffer.clear();
			}
			System.out.println("\nWriting to Console Finished");
		}
	}

	private void verifyPermissions() throws IOException {
		Path path = Paths.get(location);
		File file = path.toFile();
		boolean isWritable = Files.isWritable(path);
		System.out.println("\nIs file - " + path.getFileName() + " ReadOnly - " + !file.canWrite());
		if (isWritable) {
			file.setReadOnly();
			System.out.println("File - " + path.getFileName() + " permission changed to READ-ONLY? "
					+ String.valueOf(!file.canWrite()));
		}
	}

}
