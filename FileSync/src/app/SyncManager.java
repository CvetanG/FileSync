package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SyncManager {
	
	private SyncEntity syncEnt;
	
	public SyncManager(SyncEntity syncEnt) {
		this.syncEnt = syncEnt;
	}
	
	public void sync() {
		scanDir();
//		System.out.println(pme);

		try {
			copyFileUsingStream();
			if (this.syncEnt.isDeleteOldFiles()) {
				deleteOldFilesDirs();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done!!!");
	}
	
	private void scanDir() {
		scanDir(syncEnt.getDirSource(), this.syncEnt.getFilesSource(), this.syncEnt.getNestedDirsSource());

		if (syncEnt.isDeleteOldFiles()) {
			scanDir(this.syncEnt.getDirBackup(), this.syncEnt.getFilesBackup(), this.syncEnt.getNestedDirsBackup());
		}
	}
	
	private void scanDir(String dir, List<File> listFiles, List<File> listDirs) {

		File root = new File(dir);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {
			if (f.isDirectory() && checkDir(f)) {
				listDirs.add(f);
//					System.out.println("Dir:" + f.getAbsoluteFile());
				scanDir(f.getAbsolutePath(), listFiles, listDirs);
			} else {
				if (checkFile(f)) {
//						System.out.println("File:" + f.getAbsoluteFile());
					listFiles.add(f);
				}
			}
		}
	}
	
	private boolean checkDir(File f) {
		for (String str : this.syncEnt.getFilesDirToSkip()) {
			if (f.getAbsolutePath().startsWith(str)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkFile(File f) {
		if (!this.syncEnt.getFileTypesToSkip().isEmpty()) {
			for (String str : this.syncEnt.getFileTypesToSkip()) {
				if (f.getAbsolutePath().endsWith(str)) {
					return false;
				}
			}
		}
		for (String str : syncEnt.getFilesDirToSkip()) {
			if (f.getAbsolutePath().endsWith(str)) {
				return false;
			}
		}
		return true;
	}
	
	private void copyFileUsingStream() throws IOException {
		// create dirs
		checkAndCreateDir(new File(this.syncEnt.getDirBackup()));
		for (File dir : this.syncEnt.getNestedDirsSource()) {
			String fileName = pathAfterInputDir(dir, this.syncEnt.getDirSource());
			File dirNameBackap = new File(this.syncEnt.getDirBackup() + fileName);
			checkAndCreateDir(dirNameBackap);
		}

		for (File fileSource : this.syncEnt.getFilesSource()) {
			String fileName = pathAfterInputDir(fileSource, this.syncEnt.getDirSource());
			File fileBackup = new File(this.syncEnt.getDirBackup() + fileName);

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
	
	private void checkAndCreateDir(File dir) {

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
	    try (InputStream is = new FileInputStream(source);
	    		OutputStream os =new FileOutputStream(backup)) {
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } 
	}
	
	private void deleteOldFilesDirs() throws IOException {
		
		// delete files
		deleteFiles(this.syncEnt.getFilesBackup(), this.syncEnt.getFilesSource());
		// delete dirs
		deleteFiles(this.syncEnt.getNestedDirsBackup(), this.syncEnt.getNestedDirsSource());
	}
	
	private void deleteFiles(List<File> filesBackup, List<File> filesSource) throws IOException {
		List<String> fileNameBackup = listPathAfterInputDir(filesBackup, this.syncEnt.getDirBackup());
		List<String> fileNameSource = listPathAfterInputDir(filesSource, this.syncEnt.getDirSource());
		
		fileNameBackup.removeAll(fileNameSource);
		List<File> filesToDelete = createAbsolutePath(fileNameBackup, this.syncEnt.getDirBackup());
		
		for (File file : filesToDelete) {
			if (file.exists()) {
				deleteFileOrDir(file);
			}
		}
	}
	
	private void deleteFileOrDir(File fileDirToDel) throws IOException {
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
	
	private List<String> listPathAfterInputDir(List<File> listFiles, String inputDir) {
	    
		List<String> fileNames = new ArrayList<>();
		
		for (File file : listFiles) {
			fileNames.add(pathAfterInputDir(file, inputDir));
		}
		return fileNames;
	}
	
	private String pathAfterInputDir(File file, String inputDir) {
		return file.getAbsolutePath().replace(inputDir, "");
	}
	
	private List<File> createAbsolutePath(List<String> fileNames, String dirPath) {
	    
		List<File> files = new ArrayList<>();
		
		for (String fileName : fileNames) {
			File file = new File(dirPath + fileName);
			files.add(file);
		}
		return files;
	}
	
}
