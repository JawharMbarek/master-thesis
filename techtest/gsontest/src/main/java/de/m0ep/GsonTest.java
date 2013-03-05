package de.m0ep;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
	Gson gson = new Gson();

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("test1", "value1");
	map.put("test2", 1337);
	map.put("test3", 2.0);

	String json = gson.toJson(map);
	System.out.println(json);

	TypeToken<HashMap<String, String>> stringMapTok = new TypeToken<HashMap<String, String>>() {
	};
	Map<String, String> map2 = gson.fromJson(json, stringMapTok.getType());

	System.out.println();
	for (Entry<String, String> e : map2.entrySet())
	    System.out.println(e.getKey() + " " + e.getValue());
    }

}
