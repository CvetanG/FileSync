package app;

import java.util.List;

public class Run {
	
	private Run() {
	}
	
	public static void main(String[] args) {
		
		String fileName = "pathMapping_Linux.json";
	
		JsonToPathMapEntities reader = new JsonToPathMapEntities(fileName);
	
		List<PathMapEntity> listPathMap = reader.getListPathMap();
		for (PathMapEntity pathMapEntity : listPathMap) {
			Utils.syncDirs(pathMapEntity);
		}
	}
}
