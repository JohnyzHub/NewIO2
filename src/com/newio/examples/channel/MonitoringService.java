package com.newio.examples.channel;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class MonitoringService {

	public static void main(String[] args) {

		while (true) {
			Path path = Paths.get("Destination");
			WatchService watchService = null;
			if (Files.isDirectory(path)) {
				try {
					watchService = FileSystems.getDefault().newWatchService();
					path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_DELETE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			boolean isValid = true;
			do {
				WatchKey watchKey = null;
				try {
					watchKey = watchService.take();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (watchKey != null) {
					for (WatchEvent event : watchKey.pollEvents()) {
						if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
							System.out.println("File Created : " + event.context().toString());
						} else if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
							System.out.println("File Deleted : " + event.context().toString());
						}
					}
					isValid = watchKey.reset();
				}
			} while (isValid);
		}

	}
}
