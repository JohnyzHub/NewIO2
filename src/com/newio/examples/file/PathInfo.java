package com.newio.examples.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathInfo {

	public static void main(String[] args) throws IOException {
		File file = new File("FileChannelExample.txt");
		System.out.println("File Name : " + file.getName());
		System.out.println("Paent : " + file.getParent());
		System.out.println("Types of Path Info : ");
		System.out.println("Path : " + file.getPath());
		System.out.println("Absolute Path : " + file.getAbsolutePath());
		System.out.println("Canonical Path : " + file.getCanonicalPath());

		System.out.println("\nFile properties : ");
		System.out.println("is File : " + file.isFile());
		System.out.println("is Directory : " + file.isDirectory());
		System.out.println("is Absolute : " + file.isAbsolute());
		System.out.println("is Hidden : " + file.isHidden());
		System.out.println("Last Modified Time : " + file.lastModified());

		System.out.println("\nFile Memory properties : ");
		System.out.println("Total Size : " + file.getTotalSpace() + " Bytes");
		System.out.println("Total Free space : " + file.getFreeSpace() + " Bytes");
		System.out.println("Total usable space : " + file.getUsableSpace() + " Bytes");

		System.out.println("\nList Roots : ");
		File[] fileRoots = File.listRoots();
		for (File root : fileRoots) {
			System.out.println(root);
			System.out.println("Memory properties : ");
			System.out.println("Total Size : " + root.getTotalSpace() + " Bytes");
			System.out.println("Total Free space : " + root.getFreeSpace() + " Bytes");
			System.out.println("Total usable space : " + root.getUsableSpace() + " Bytes");

		}

		System.out.println("\nFilter files list with 'txt' : ");
		Path sourcePath = Paths.get("sourceDir");
		File sourceDir = sourcePath.toFile();
		FilenameFilter filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith("txt");
			}
		};

		String[] fileNameList = sourceDir.list(filter);
		for (String fileName : fileNameList) {
			System.out.println("File :" + fileName);
		}
	}
}
