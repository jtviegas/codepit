package org.challenges.norcom.indexer.services.unzipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UnzipperImpl implements Unzipper {

	private static final Logger logger = LoggerFactory.getLogger(UnzipperImpl.class);

	private Path folder;

	public UnzipperImpl() throws IOException {
		logger.trace("[ () ] in");

		String destinationFolder = String.format("%s%s%s", System.getProperty("java.io.tmpdir"),
				System.getProperty("file.separator"), UUID.randomUUID().toString());
		folder = Paths.get(destinationFolder);
		folder.toFile().mkdirs();

		logger.trace("[ () ] out");
	}

	@Override
	public Path unzip(Path zipFile) throws IOException {
		logger.trace("[ unzip ] in - zipFile: {}", zipFile.toString());

		if (!Files.probeContentType(zipFile).equalsIgnoreCase("application/zip"))
			throw new IllegalArgumentException("not a zip file");

		final File file = zipFile.toFile();

		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
		try {
			ZipEntry zipEntry = zis.getNextEntry();

			while (zipEntry != null) {
				String fileName = zipEntry.getName();
				File newFile = new File(
						folder.toFile().getAbsolutePath() + System.getProperty("file.separator") + fileName);
				if (zipEntry.isDirectory()) {
					newFile.mkdirs();
				} else {
					FileOutputStream fos = new FileOutputStream(newFile);
					int len;
					try {
						while ((len = zis.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
					} finally {
						fos.close();
					}
				}

				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
			logger.debug("[ unzip ] returning: {}", folder.toString());
			return folder;
		} finally {
			zis.close();
			logger.trace("[ unzip ] out");
		}

	}

}
