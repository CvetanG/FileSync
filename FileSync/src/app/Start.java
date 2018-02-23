package app;

import java.io.File;
import java.io.IOException;

public class Start {
	public static void main(String[] args) throws IOException {

		FileSync fSync = new FileSync();

		// String pathSource = "D:\\Tavex_01.xlsx";

		String fileName = "install wls.txt";

		String dirSource = "/home/cvetan/Downloads/";
		String dirBackup = "/home/cvetan/cecotemp/";

		String pathSource = dirSource + fileName;
		String pathBackup = dirBackup + fileName;

		File fileSource = new File(pathSource);
		// System.out.println(fileSource.getName());
		// System.out.println(fileSource.lastModified());

		File fileBackup = new File(pathBackup);

		/* test walk */
		// fSync.walk(dirSource);

		/* test deleteOldFiles */
		if (fSync.deleteOldFiles) {
			fSync.deleteOldFiles(dirBackup, dirSource);
		}

		/* check and copy files */
		// if (!fileBackup.exists() || fileSource.lastModified() >
		// fileBackup.lastModified()) {
		// fSync.checkAndCreateDir(dirBackup);
		// System.out.println("Copy/updated file: " +
		// fileBackup.getAbsolutePath());
		// fSync.copyFileUsingStream(fileSource, fileBackup);
		// } else {
		// System.out.println(fileBackup.getAbsolutePath());
		// System.out.println(fileBackup.lastModified());
		// }

	}
}
