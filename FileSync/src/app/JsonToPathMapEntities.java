package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonToPathMapEntities {

	private static final String ARRAY_KEY = "dirs";

	private List<PathMapEntity> listPathMap;

	public JsonToPathMapEntities(String fileName) {
		JSONObject json = read(fileName);
		this.listPathMap = objectToPathMap(json);
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

	private List<PathMapEntity> objectToPathMap(JSONObject json) {

		List<PathMapEntity> result = new ArrayList<>();

		ObjectMapper m = new ObjectMapper();
		JSONArray jsonArray = json.getJSONArray(ARRAY_KEY);
		for (int i = 0; i < jsonArray.length(); i++) {
			PathMapEntity pm = null;
			JSONObject jsonChild = jsonArray.getJSONObject(i);
			try {
				pm = m.readValue(jsonChild.toString(), PathMapEntity.class);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (pm != null) {
				pm.setFilesSource(new ArrayList<File>());
				pm.setNestedDirsSource(new ArrayList<File>());
				pm.setFilesBackup(new ArrayList<File>());
				pm.setNestedDirsBackup(new ArrayList<File>());
				result.add(pm);
			}
		}
		return result;
	}

	public List<PathMapEntity> getListPathMap() {
		return listPathMap;
	}

	public static void main(String[] args) {

		String fileName = "pathMapping_Linux.json";

		JsonToPathMapEntities reader = new JsonToPathMapEntities(fileName);

		List<PathMapEntity> listPathMap = reader.getListPathMap();

		for (PathMapEntity pathMap : listPathMap) {
			System.out.println(pathMap);

		}

	}
}
