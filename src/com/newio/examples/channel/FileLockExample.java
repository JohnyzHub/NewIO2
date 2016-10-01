package com.newio.examples.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLockExample {

	private final static int MAXCOUNT = 150000;
	private static final int RECLEN = 16;

	private static ByteBuffer buffer = ByteBuffer.allocate(RECLEN);
	private static IntBuffer intBuffer = buffer.asIntBuffer();

	private static int counter = 1;

	public static void main(String[] args) {
		boolean writer = false;
		if (args.length > 0) {
			writer = true;
		}

		try (RandomAccessFile file = new RandomAccessFile("resources\\FileLockExample.txt", (writer) ? "rw" : "r");
				FileChannel fileChannel = file.getChannel();) {
			if (writer) {
				update(fileChannel);
			} else {
				query(fileChannel);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void query(FileChannel fc) throws IOException {
		for (int i = 0; i < MAXCOUNT; i++) {
			System.out.println("Acquiring Shared Lock");
			FileLock fileLock = fc.lock(0, RECLEN, true);
			if ((i % 10000) == 0) {
				try {
					Thread.sleep(4000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				buffer.clear();
				fc.read(buffer, 0);
				int a = intBuffer.get(0);
				int b = intBuffer.get(1);
				int c = intBuffer.get(2);
				int d = intBuffer.get(3);
				System.out.println("Readig : " + a + " " + b + " " + c + " " + d);
				if (a * 2 != b || a * 3 != c || a * 4 != d) {
					System.out.println("Error");
					return;
				}
			} finally {
				fileLock.release();
			}
		}

	}

	private static void update(FileChannel fileChannel) throws IOException {

		for (int i = 0; i < MAXCOUNT; i++) {
			System.out.println("Acquiring Exclusive Lock");
			FileLock fileLock = fileChannel.lock(0, RECLEN, false);
			try {
				intBuffer.clear();
				int a = counter;
				int b = counter * 2;
				int c = counter * 3;
				int d = counter * 4;
				System.out.println("Writing : a - " + a + " b - " + b + " c - " + c + " d - " + d);
				intBuffer.put(a);
				intBuffer.put(b);
				intBuffer.put(c);
				intBuffer.put(d);
				counter++;
				buffer.clear();
				fileChannel.write(buffer, 0);
			} finally {
				fileLock.release();
			}
		}

	}
}