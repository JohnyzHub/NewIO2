package com.newio.examples.channel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

public class UsersList {

	public static void main(String[] args) {

		Path existingFile = Paths.get("c:\\Users\\Public");
		FileOwnerAttributeView attributeView = Files.getFileAttributeView(existingFile, FileOwnerAttributeView.class);
		UserPrincipal userPrincipal = null;
		try {
			userPrincipal = attributeView.getOwner();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ownerName = userPrincipal.getName();
		System.out.println("File Owner : " + ownerName);
	}

}
