package com.newio.examples.channel;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelExample {
	public static void main(String[] args) {
		fromFileToConsole();

	}

	private static void fromFileToConsole() {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile("FileChannelExample.txt", "r");
				FileOutputStream outStream = new FileOutputStream("FileChannelOut.txt");
				FileChannel fileChannel1 = randomAccessFile.getChannel();
				FileChannel fileChannel2 = outStream.getChannel()) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(512);
			int bytesRead = fileChannel1.read(byteBuffer);
			while (bytesRead > 0) {
				System.out.println("\n\nNumber of Bytes Read : " + bytesRead + " File's current position : "
						+ fileChannel1.position() + "\n");
				byteBuffer.flip();
				while (byteBuffer.hasRemaining()) {
					fileChannel2.write(byteBuffer);
				}
				byteBuffer.clear();
				bytesRead = fileChannel1.read(byteBuffer);
			}
			System.out.println("Finished Reading from FileChannelExample.txt");
			printFileContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printFileContent() throws IOException {
		System.out.println("Printing data from FileChannelOut.txt \n\n");
		try (FileReader fileReader = new FileReader("FileChannelOut.txt");
				BufferedReader bufferReader = new BufferedReader(fileReader)) {
			String textRead = bufferReader.readLine();
			while (textRead != null) {
				System.out.println(textRead);
				textRead = bufferReader.readLine();
			}
		}
	}
}