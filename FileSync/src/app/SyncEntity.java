package app;

import java.io.File;
import java.util.List;

public class SyncEntity {
	
	private String dirSource;
	private String dirBackup;
	private boolean deleteOldFiles;
	private List<String> filesDirToSkip;
	private List<String> fileTypesToSkip;

	private List<File> filesSource;
	private List<File> nestedDirsSource;
	private List<File> filesBackup;
	private List<File> nestedDirsBackup;
    
	public String getDirSource() {
		return dirSource;
	}

	public void setDirSource(String dirSource) {
		this.dirSource = dirSource;
	}

	public String getDirBackup() {
		return dirBackup;
	}

	public void setDirBackup(String dirBackup) {
		this.dirBackup = dirBackup;
	}
	
	public boolean isDeleteOldFiles() {
		return deleteOldFiles;
	}

	public void setDeleteOldFiles(boolean deleteOldFiles) {
		this.deleteOldFiles = deleteOldFiles;
	}

	public List<String> getFilesDirToSkip() {
		return filesDirToSkip;
	}

	public void setFilesDirToSkip(List<String> filesDirToSkip) {
		this.filesDirToSkip = filesDirToSkip;
	}

	public List<File> getFilesSource() {
		return filesSource;
	}

	public void setFilesSource(List<File> filesSource) {
		this.filesSource = filesSource;
	}

	public List<File> getFilesBackup() {
		return filesBackup;
	}

	public void setFilesBackup(List<File> filesBackup) {
		this.filesBackup = filesBackup;
	}

	public List<File> getNestedDirsSource() {
		return nestedDirsSource;
	}

	public void setNestedDirsSource(List<File> nestedDirsSource) {
		this.nestedDirsSource = nestedDirsSource;
	}

	public List<File> getNestedDirsBackup() {
		return nestedDirsBackup;
	}

	public void setNestedDirsBackup(List<File> nestedDirsBackup) {
		this.nestedDirsBackup = nestedDirsBackup;
	}
	
	public List<String> getFileTypesToSkip() {
		return fileTypesToSkip;
	}

	public void setFileTypesToSkip(List<String> fileTypesToSkip) {
		this.fileTypesToSkip = fileTypesToSkip;
	}

	@Override
	public String toString() {
		return "SyncEntity [dirSource=" + dirSource + ", dirBackup=" + dirBackup + ", deleteOldFiles=" + deleteOldFiles
				+ ", filesDirToSkip=" + filesDirToSkip + ", fileTypesToSkip=" + fileTypesToSkip + ", filesSource="
				+ filesSource + ", nestedDirsSource=" + nestedDirsSource + ", filesBackup=" + filesBackup
				+ ", nestedDirsBackup=" + nestedDirsBackup + "]";
	}
	
	
}
