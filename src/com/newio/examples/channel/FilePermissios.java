package com.newio.examples.channel;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class FilePermissios {

	public static void main(String[] args) {

		Path newDir = Paths.get("TestDir");
		Path existingFile = Paths.get("FileChannelExample.txt");

		FileOwnerAttributeView attributeView = Files.getFileAttributeView(existingFile, FileOwnerAttributeView.class);
		UserPrincipal userPrincipal1 = null;
		try {
			userPrincipal1 = attributeView.getOwner();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String ownerName1 = userPrincipal1.getName();
		System.out.println("File Owner : " + ownerName1);

		Path existingFile2 = Paths.get("C:\\Users\\Public");
		FileOwnerAttributeView attributeView1 = Files.getFileAttributeView(existingFile2, FileOwnerAttributeView.class);
		UserPrincipal userPrincipal2 = null;
		try {
			userPrincipal2 = attributeView1.getOwner();
		} catch (IOException e1) { // TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ownerName2 = userPrincipal2.getName();
		System.out.println("File Owner : " + ownerName2);

		if (Files.notExists(newDir)) {
			System.out.println("Directory" + newDir.getFileName() + " doesn't exist");

			FileAttribute<List<AclEntry>> fileAttribute = new FileAttribute<List<AclEntry>>() {

				@Override
				public String name() {
					return "acl:acl";
				}

				@Override
				public List<AclEntry> value() {
					UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
					UserPrincipal newUserPricipal1 = null;
					UserPrincipal newUserPricipal2 = null;
					try {
						newUserPricipal1 = lookupService.lookupPrincipalByName(ownerName1);
						newUserPricipal2 = lookupService.lookupPrincipalByName(ownerName2);
					} catch (IOException e) {
						e.printStackTrace();
					}
					Set<AclEntryFlag> aclFlags = EnumSet.of(AclEntryFlag.FILE_INHERIT, AclEntryFlag.DIRECTORY_INHERIT);
					Set<AclEntryPermission> aclPermissions = EnumSet.of(AclEntryPermission.READ_DATA,
							AclEntryPermission.WRITE_DATA, AclEntryPermission.DELETE, AclEntryPermission.DELETE_CHILD);

					AclEntry aclEntry1 = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setFlags(aclFlags)
							.setPermissions(aclPermissions).setPrincipal(newUserPricipal1).build();
					AclEntry aclEntry2 = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setFlags(aclFlags)
							.setPermissions(aclPermissions).setPrincipal(newUserPricipal2).build();
					List<AclEntry> aclEntryList = new ArrayList<>(2);
					aclEntryList.add(aclEntry1);
					aclEntryList.add(aclEntry2);
					return aclEntryList;
				}
			};
			try {
				Files.createDirectory(newDir, fileAttribute);
				System.out.println("Directory Created");
			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Directory " + newDir + " already exist");
			return;
		}
		System.out.println("Directory " + newDir + " Writable - " + String.valueOf(Files.isWritable(newDir)));
		System.out.println("Directory " + newDir + " Readable - " + String.valueOf(Files.isReadable(newDir)));
		System.out.println("Directory " + newDir + " Regular - " + String.valueOf(Files.isRegularFile(newDir)));
		File file = newDir.toFile();
		file.setReadOnly();
		System.out.println("Directory " + newDir + " Writable - " + String.valueOf(Files.isWritable(newDir)));
		file.setWritable(true);
		System.out.println("Directory " + newDir + " Writable - " + String.valueOf(Files.isWritable(newDir)));

	}
}