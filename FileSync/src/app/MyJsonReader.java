package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJsonReader {

	final String filaName = "pathMapping.json";

	File fileSource = new File(filaName);

	public JSONObject read() throws IOException {

		String content = FileUtils.readFileToString(fileSource, "utf-8");
		JSONObject json = new JSONObject(content);
		return json;

	}
	/*
	public static List<Object> getListFromJsonObject(JSONObject jObject) throws JSONException {
		List<Object> returnList = new ArrayList<Object>();
		Iterator<String> keys = jObject.keys();

		List<String> keysList = new ArrayList<String>();
		while (keys.hasNext()) {
			keysList.add(keys.next());
		}
		Collections.sort(keysList);

		for (String key : keysList) {
			List<Object> nestedList = new ArrayList<Object>();
			nestedList.add(key);
			nestedList.add(convertJsonItem(jObject.get(key)));
			returnList.add(nestedList);
		}

		return returnList;
	}

	public static Object convertJsonItem(Object o) throws JSONException {
		if (o == null) {
			return "null";
		}

		if (o instanceof JSONObject) {
			return getListFromJsonObject((JSONObject) o);
		}

		if (o instanceof JSONArray) {
			return getListFromJsonArray((JSONArray) o);
		}

		if (o.equals(Boolean.FALSE) || (o instanceof String && ((String) o).equalsIgnoreCase("false"))) {
			return false;
		}

		if (o.equals(Boolean.TRUE) || (o instanceof String && ((String) o).equalsIgnoreCase("true"))) {
			return true;
		}

		if (o instanceof Number) {
			return o;
		}

		return o.toString();
	}

	public static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
		List<String> returnList = new ArrayList<String>();
		for (int i = 0; i < jArray.length(); i++) {
			String val = jArray.getString(i);
			returnList.add(val);
		}
		return returnList;
	}
	
	public static List<Object> getListFromJsonArray(JSONArray jArray) throws JSONException {
	      List<Object> returnList = new ArrayList<Object>();
	      for (int i = 0; i < jArray.length(); i++) {
	        returnList.add(convertJsonItem(jArray.get(i)));
	      }
	      return returnList;
	    }
*/
	public static void main(String[] args) throws IOException {
		MyJsonReader reader = new MyJsonReader();

		JSONObject jsonMain = reader.read();
		
		Map<String, Object> map = jsonMain.toMap();
		
		List<PathMap> pathMaps = new ArrayList<>();
		
		for (Object value : map.values()) {
			System.out.println(value);
		}
		
		System.out.println("Exit");
	}
}
