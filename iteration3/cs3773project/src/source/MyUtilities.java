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
		String arrayandsalt=  jarray.toString() + salt;
		String hash = CryptoStuff.hashSha256(arrayandsalt);
		sha.put("checksum",  hash);
		
		copyarray.put(sha);
		
		return copyarray.toString();
	}
}
