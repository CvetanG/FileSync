package app;

import java.util.List;

public class PathMap {
	
	String pathSourse;
    String pathBackup;
    List<String> pathsToSkip;
    
    public PathMap objectToPathMap(Object o) {
    	PathMap pathMap = new PathMap();
    	
    	String str = o.toString();
    	
		return pathMap;
    	
    }
    
	public String getPathSourse() {
		return pathSourse;
	}
	public void setPathSourse(String pathSourse) {
		this.pathSourse = pathSourse;
	}
	public String getPathBackup() {
		return pathBackup;
	}
	public void setPathBackup(String pathBackup) {
		this.pathBackup = pathBackup;
	}
	public List<String> getPathsToSkip() {
		return pathsToSkip;
	}
	public void setPathsToSkip(List<String> pathsToSkip) {
		this.pathsToSkip = pathsToSkip;
	}
	
	private StringBuilder printPathsToSkip() {
		StringBuilder srtBuilder = new StringBuilder();
		srtBuilder.append('{');
		for (String str : pathsToSkip) {
			srtBuilder.append(str);
		}
		srtBuilder.append('}');
		return srtBuilder;
	}
	
	@Override
	public String toString() {
		StringBuilder b = printPathsToSkip();
		return "PathMap [pathSourse=" + pathSourse + ","
				+ " pathBackup=" + pathBackup + ","
						+ " pathsToSkip=" + 
						b
				+ "]";
	}

	public static void main(String[] args) {
		PathMap pm = new PathMap();
		Object o = "{ + "
				+ "pathBackup=example path backup 1, "
				+ "pathSourse=example path source 1,"
				+ "pathsToSkip={skipPath1=example path skip 1.1, skipPath2=example path skip 1.2}"
				+ "}";
		
		PathMap newPathMap = pm.objectToPathMap(o);
		System.out.println(newPathMap.toString());

	}
}
