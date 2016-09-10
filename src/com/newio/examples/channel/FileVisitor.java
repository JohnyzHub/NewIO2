package com.newio.examples.channel;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitor extends SimpleFileVisitor<Path> {

	private final PathMatcher pathMatcher;

	private int fileCounter = 0;

	public FileVisitor(String pattern) {
		pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		System.out.println("Before visiting Directory : " + dir.getFileName());
		if (pathMatcher.matches(dir)) {
			System.out.println("File Visited : " + dir.getFileName() + " - Creation Time : " + attrs.creationTime());
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

		System.out.println("Inside VisitFile");
		if (pathMatcher.matches(file)) {
			System.out.println("File Visited : " + file.getFileName() + " - Creation Time : " + attrs.creationTime());
			++fileCounter;
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

		System.out.println("After visiting Directory : " + dir.getFileName());
		if (pathMatcher.matches(dir)) {
			System.out.println("File Visited : " + dir.getFileName());
		}
		return FileVisitResult.CONTINUE;
	}

	private int getCounter() {
		return fileCounter;
	}

	public static void main(String[] args) {
		Path path = Paths.get(".");
		FileVisitor fileVisitor = new FileVisitor("**/*.txt");
		try {
			Files.walkFileTree(path, fileVisitor);
			System.out.println("Files Matched : " + fileVisitor.getCounter());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
