package com.newio.examples.channel;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class MonitoringDirectory {

	public static void main(String[] args) {
		try {
			Path newDir = Paths.get("Destination");
			// Delete all the files inside the folder.
			Files.walkFileTree(newDir, new MonitoringDirectory().new FileVisitor());

			// Create folder only if it doesn't exist
			if (Files.notExists(newDir)) {
				Files.createDirectory(newDir);
				System.out.println("Directory Created: " + newDir);
			}

			// File path : Destination\\File1.txt
			Files.createFile(newDir.resolve("File1.txt"));
			System.out.println("File Created : File1.txt");
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class FileVisitor extends SimpleFileVisitor<Path> {

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Files.deleteIfExists(file);
			System.out.println("File Deleted : " + file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		/**
		 * -------To delete the directory ------
		 * 
		 * @Override public FileVisitResult postVisitDirectory(Path dir,
		 *           IOException exc) throws IOException {
		 *           Files.deleteIfExists(dir); System.out.println("Directory
		 *           Deleted : " + dir.getFileName()); return
		 *           FileVisitResult.CONTINUE; }
		 */
	}

}