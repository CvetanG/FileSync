package app;

import java.util.List;

public class Run {
	
	private Run() {
	}
	
	public static void main(String[] args) {
		
		String fileName = "pathMapping_Linux.json";
	
		JsonToSyncEntities reader = new JsonToSyncEntities(fileName);
	
		List<SyncEntity> syncEntities = reader.getListSyncEnt();
		for (SyncEntity syncEnt : syncEntities) {
			SyncManager manager = new SyncManager(syncEnt);
			manager.sync();
		}
	}
}
