package com.example.ortel.tagnet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}



	public JSONObject getJSONFromUrlByGet(String url) {

		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(url)
					.build();
			Response responses = null;

			try {
				responses = client.newCall(request).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String jsonData = responses.body().string();
			JSONObject Jobject = new JSONObject(jsonData);


			return Jobject;
		} catch (IOException e) {
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}


}
