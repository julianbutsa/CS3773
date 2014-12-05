package source;

import java.security.InvalidParameterException;

import madmarcos.CryptoStuff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyUtilities {

	public static JSONObject getJSONObject(String key, JSONArray jarray) {
		// TODO Auto-generated method stub
		if(key == null)
		return null;
			//do something
		if(jarray == null)
			return null;
			//do something
		//System.out.println(jarray.toString());
		
		try{
			for(int i = 0; i < jarray.length();i++){
				Object temp = jarray.get(i);
				if(temp instanceof JSONObject){
					try{
						((JSONObject)temp).get(key);
						return (JSONObject)temp;
					}catch(JSONException e){
						
					}
					//if(((JSONObject)temp).get(key) != null)
						//return (JSONObject) temp;
				}				
			}
		}catch(JSONException e){
			System.out.printf("error: %s\n", e.toString());
		}
		return null;
		
	}

	
	public static String sha256adder(JSONArray jarray, String salt){
		if(jarray == null)
			throw new InvalidParameterException("Cannot sha256 a null JSONArray.");
		
		JSONArray copyarray = new JSONArray(jarray.toString());
		JSONObject sha = new JSONObject();
		System.out.printf("Salt + string: %s\n", salt+jarray.toString());
		sha.put("checksum", CryptoStuff.hashSha256(salt + jarray.toString()) );
		
		copyarray.put(sha);
		
		return copyarray.toString();
	}
}
