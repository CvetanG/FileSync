package app;

import java.io.File;
import java.util.List;

public class PathMapEntity {
	
	private String dirSource;
	private String dirBackup;
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

	private StringBuilder printPathsToSkip() {
		StringBuilder srtBuilder = new StringBuilder();
		srtBuilder.append('[');
		for (int i = 0; i < filesDirToSkip.size(); i++) {
			srtBuilder.append(filesDirToSkip.get(i));
			if (i < (filesDirToSkip.size() -1)) {
				srtBuilder.append(", ");
			}
		}
		srtBuilder.append(']');
		return srtBuilder;
	}
	
	@Override
	public String toString() {
		StringBuilder b = printPathsToSkip();
		return "PathMap {pathSource=" + dirSource + ","
				+ " pathBackup=" + dirBackup + ","
						+ " pathsToSkip=" + 
						b
				+ "}";
	}

}
