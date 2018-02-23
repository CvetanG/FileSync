package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileSync {
	
	final boolean deleteOldFiles = true;
	
	List<String> listPathSource;
	List<String> listPathBackup;
	List<String> listPathSkip;
	
	private void walk(String path, List<File> files) {
		
		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {
			if (f.isDirectory()) {
				files.add(f);
//				System.out.println("Dir:" + f.getAbsoluteFile());
				walk(f.getAbsolutePath(), files);
			} else {
//				System.out.println("File:" + f.getAbsoluteFile());
				files.add(f);
			}
		}
	}
	
	private void checkAndCreateDir(String path) {

		File dir = new File(path);

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

	private void copyFileUsingStream(File source, File backup) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(backup);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
	
	private void deleteOldFiles(String dirBackup, String dirSource) throws IOException {
	    
		List<File> filesBackup = new ArrayList<>();
		this.walk(dirBackup, filesBackup);
		List<File> filesSource = new ArrayList<>();
		this.walk(dirSource, filesSource);

		List<String> fileNamesBackup = this.getFileName(filesBackup);
		List<String> fileNamesSource = this.getFileName(filesSource);
		
		fileNamesBackup.removeAll(fileNamesSource);
		List<File> filesToDelete = this.createFilePath(fileNamesBackup, dirBackup);
		
		for (File file : filesToDelete) {
			System.out.println("Delete file: " + file.getAbsolutePath());
			if (file.exists()) {
				deleteDir(file);
			}
		}
	}
	
	private void deleteDir(File file) {
	    File[] contents = file.listFiles();
	    if (contents != null) {
	        for (File f : contents) {
	            deleteDir(f);
	        }
	    }
	    file.delete();
	}
	
	private List<String> getFileName(List<File> files) throws IOException {
	    
		List<String> fileNames = new ArrayList<>();
		
		for (File file : files) {
			fileNames.add(file.getName());
		}
		return fileNames;
		
	}
	
	private List<File> createFilePath(List<String> fileNames, String dir) throws IOException {
	    
		List<File> files = new ArrayList<>();
		
		for (String fileName : fileNames) {
			String pathSource = dir + fileName;
			File file = new File(pathSource);
			files.add(file);
		}
		return files;
		
	}
	
	public static void main(String[] args) throws IOException {
		
		FileSync fSync = new FileSync();
		
//		String pathSource = "D:\\Tavex_01.xlsx";
		
		String fileName = "install wls.txt";
		
		String dirSource = "/home/cvetan/Downloads/";
		String dirBackup = "/home/cvetan/cecotemp/";
		
		String pathSource = dirSource + fileName;
		String pathBackup = dirBackup + fileName;
		
		File fileSource = new File(pathSource);
//		System.out.println(fileSource.getName());
//		System.out.println(fileSource.lastModified());
		
		File fileBackup = new File(pathBackup);
		
		/* test walk */
//		fSync.walk(dirSource);
		
		/* test deleteOldFiles */
		if (fSync.deleteOldFiles) {
			fSync.deleteOldFiles(dirBackup, dirSource);
		}
		
		/* check and copy files */
//		if (!fileBackup.exists() || fileSource.lastModified() > fileBackup.lastModified()) {
//			fSync.checkAndCreateDir(dirBackup);
//			System.out.println("Copy/updated file: " + fileBackup.getAbsolutePath());
//			fSync.copyFileUsingStream(fileSource, fileBackup);
//		} else {
//			System.out.println(fileBackup.getAbsolutePath());
//			System.out.println(fileBackup.lastModified());
//		}
		
		
	}
	
}
