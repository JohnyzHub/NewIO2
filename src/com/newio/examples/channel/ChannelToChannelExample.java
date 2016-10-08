package com.newio.examples.channel;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class ChannelToChannelExample {

	public static void main(String[] args) {

		File file = new File("resources\\ChannelToFile.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Path filePath1 = Paths.get("resources\\ChannelFromFile.txt");
		Path filePath2 = file.toPath();

		try (FileChannel channelFrom = FileChannel.open(filePath1, StandardOpenOption.READ);
				FileChannel channelTo = FileChannel.open(filePath2, StandardOpenOption.WRITE);) {

			channelTo.transferFrom(channelFrom, 0, channelFrom.size());
			try (Stream<String> lines = Files.lines(filePath2).onClose(() -> System.out.println(""));) {
				lines.forEach(line -> System.out.println(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.deleteOnExit();
	}

}
