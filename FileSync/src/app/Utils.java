package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	private static final boolean DELETE_OLD_FILES = true;
	private static final boolean CHECK_FILE_TYPES = true;
	private Utils() {
	}
	
	
	public static void syncDirs(PathMapEntity pme) {
		scanDir(pme);
		try {
			if (DELETE_OLD_FILES) {
				deleteOldFiles(pme);
			}
			copyFileUsingStream(pme);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done!!!");
	}
	
	private static void scanDir(PathMapEntity pme) {
		scanDir(pme.getDirSource(), pme.getFilesSource(), pme.getNestedDirsSource(), pme);
		scanDir(pme.getDirBackup(), pme.getFilesBackup(), pme.getNestedDirsBackup(), pme);
	}
	
	private static void scanDir(String dir, List<File> listFiles, List<File> listDirs, PathMapEntity pme) {

		File root = new File(dir);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {
			if (f.isDirectory() && checkDir(f, pme)) {
				listDirs.add(f);
//					System.out.println("Dir:" + f.getAbsoluteFile());
				scanDir(f.getAbsolutePath(), listFiles, listDirs, pme);
			} else {
				if (checkFile(f, pme)) {
//						System.out.println("File:" + f.getAbsoluteFile());
					listFiles.add(f);
				}
			}
		}
	}
	
	private static boolean checkDir(File f, PathMapEntity pme) {
		for (String str : pme.getFilesDirToSkip()) {
			if (f.getAbsolutePath().startsWith(str)) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean checkFile(File f, PathMapEntity pme) {
		if (CHECK_FILE_TYPES) {
			for (String str : pme.getFileTypesToSkip()) {
				if (f.getAbsolutePath().endsWith(str)) {
					return false;
				}
			}
		}
		for (String str : pme.getFilesDirToSkip()) {
			if (f.getAbsolutePath().endsWith(str)) {
				return false;
			}
		}
		return true;
	}
	
	private static void copyFileUsingStream(PathMapEntity pme) throws IOException {
		// create dirs
		for (File dir : pme.getNestedDirsSource()) {
			String fileName = dir.getAbsolutePath().replace(pme.getDirSource(), "");
			File dirNameBackap = new File(pme.getDirBackup() + fileName);
			checkAndCreateDir(dirNameBackap);
		}

		for (File fileSource : pme.getFilesSource()) {
			String fileName = fileSource.getAbsolutePath().replace(pme.getDirSource(), "");
			File fileBackup = new File(pme.getDirBackup() + fileName);

			if (!fileBackup.exists() || fileSource.lastModified() > fileBackup.lastModified()) {

				File[] contents = fileSource.listFiles();
				if (contents != null) {
					checkAndCreateDir(fileBackup);
				}
				System.out.println("Copy/Update file: " + fileBackup.getAbsolutePath());
				copyFileUsingStream(fileSource, fileBackup);
			}

		}
	}
	
	private static void checkAndCreateDir(File dir) {

		// if the directory does not exist, create it
		if (!dir.exists()) {
			boolean result = false;

			try {
				dir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("Created: " + dir.getAbsolutePath());
			}
		}
	}
	
	private static void copyFileUsingStream(File source, File backup) throws IOException {
	    try (InputStream is = new FileInputStream(source);
	    		OutputStream os =new FileOutputStream(backup)) {
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } 
	}
	
	private static void deleteOldFiles(PathMapEntity pme) throws IOException {
	    
		List<File> filesBackup = pme.getFilesBackup();
		List<File> filesSource = pme.getFilesSource();

		List<String> fileNameBackup = getFileName(filesBackup);
		List<String> fileNameBackupSource = getFileName(filesSource);
		
		fileNameBackup.removeAll(fileNameBackupSource);
		List<File> filesToDelete = createAbsolutePath(fileNameBackup, pme.getDirBackup());
		
		for (File file : filesToDelete) {
			if (file.exists()) {
				deleteFileOrDir(file);
			}
		}
	}
	
	private static void deleteFileOrDir(File fileDirToDel) throws IOException {
	    File[] contents = fileDirToDel.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	        	System.out.println("Delete file: " + f.getAbsolutePath());
	        	deleteFileOrDir(f);
	        }
	    }
	    if (fileDirToDel.delete()) {
	    	System.out.println("Delete file: " + fileDirToDel.getAbsolutePath());
		} else {
			System.out.println("Fail to Delete: " + fileDirToDel.getAbsolutePath());
			throw new IOException();
		}
	}
	
	private static List<String> getFileName(List<File> listAbsolutePath) {
	    
		List<String> fileNames = new ArrayList<>();
		
		for (File file : listAbsolutePath) {
			fileNames.add(file.getName());
		}
		return fileNames;
		
	}
	
	private static List<File> createAbsolutePath(List<String> fileNames, String dirPath) {
	    
		List<File> files = new ArrayList<>();
		
		for (String fileName : fileNames) {
			String pathSource = dirPath + fileName;
			File file = new File(pathSource);
			files.add(file);
		}
		return files;
		
	}
}
