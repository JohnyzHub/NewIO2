package com.newio.examples.channel.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class PipeDemo {

	public static void main(String[] args) {

		try {
			Pipe pipe = Pipe.open();
			Runnable senderTask = new Runnable() {

				@Override
				public void run() {
					try (WritableByteChannel writeChannel = pipe.sink();) {
						ByteBuffer byteBuffer = ByteBuffer.allocate(10);

						for (int i = 0; i < 3; i++) {
							System.out.println("Iteration : " + i);
							byteBuffer.clear();
							for (int j = 0; j < 10; j++) {
								byteBuffer.put((byte) (256 * Math.random()));
							}
							byteBuffer.flip();
							while (writeChannel.write(byteBuffer) > 0)
								;
						}
					} catch (Exception e) {
					}
				}
			};
			Runnable receiverTask = new Runnable() {

				@Override
				public void run() {
					try (ReadableByteChannel readChannel = pipe.source()) {
						ByteBuffer byteBuffer = ByteBuffer.allocate(10);
						while (readChannel.read(byteBuffer) > 0) {
							// Thread.sleep(1000);
							byteBuffer.flip();
							while (byteBuffer.hasRemaining()) {
								System.out.println(byteBuffer.get());
							}
							byteBuffer.clear();
						}
					} catch (Exception e) {
					}
				}
			};

			Thread t1 = new Thread(senderTask, "senderThread");
			Thread t2 = new Thread(receiverTask, "receiverThread");
			t2.start();
			t1.start();
		} catch (Exception e) {
		}
	}
}