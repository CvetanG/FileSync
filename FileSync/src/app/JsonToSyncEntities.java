package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonToSyncEntities {

	private static final String ARRAY_KEY = "dirs";

	private List<SyncEntity> listSyncEnt;

	public JsonToSyncEntities(String fileName) {
		JSONObject json = read(fileName);
		this.listSyncEnt = objectToSyncEnt(json);
	}

	private JSONObject read(String fileName) {
		File fileSource = new File(fileName);
		String content = null;
		try {
			content = FileUtils.readFileToString(fileSource, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new JSONObject(content);
	}

	private List<SyncEntity> objectToSyncEnt(JSONObject json) {

		List<SyncEntity> result = new ArrayList<>();

		ObjectMapper m = new ObjectMapper();
		JSONArray jsonArray = json.getJSONArray(ARRAY_KEY);
		for (int i = 0; i < jsonArray.length(); i++) {
			SyncEntity se = null;
			JSONObject jsonChild = jsonArray.getJSONObject(i);
			try {
				se = m.readValue(jsonChild.toString(), SyncEntity.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (se != null) {
				se.setFilesSource(new ArrayList<File>());
				se.setNestedDirsSource(new ArrayList<File>());
				se.setFilesBackup(new ArrayList<File>());
				se.setNestedDirsBackup(new ArrayList<File>());
				result.add(se);
			}
		}
		return result;
	}

	public List<SyncEntity> getListSyncEnt() {
		return listSyncEnt;
	}

	public static void main(String[] args) {

		String fileName = "pathMapping_Linux.json";

		JsonToSyncEntities reader = new JsonToSyncEntities(fileName);

		List<SyncEntity> listPathMap = reader.getListSyncEnt();

		for (SyncEntity pathMap : listPathMap) {
			System.out.println(pathMap);

		}

	}
}
