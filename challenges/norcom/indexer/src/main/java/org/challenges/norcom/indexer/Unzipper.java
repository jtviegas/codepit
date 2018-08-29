package org.challenges.norcom.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

	private File file;
	private File folder;

	public Unzipper(Path zipFile, Path folder) throws IOException {

		if (!Files.probeContentType(zipFile).equalsIgnoreCase("application/zip"))
			throw new IllegalArgumentException("not a zip file");

		if (!(Files.exists(folder) && Files.isDirectory(folder) && Files.isWritable(folder))) {
			throw new IllegalArgumentException("not a writable directory");
		}

		file = zipFile.toFile();
		this.folder = folder.toFile();
	}

	public void unzip() throws IOException {

		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
		try {
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				String fileName = zipEntry.getName();
				File newFile = new File(folder.getAbsolutePath() + System.getProperty("file.separator") + fileName);
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				try {
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
				} finally {
					fos.close();
				}
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
		} finally {
			zis.close();
		}

	}

}
