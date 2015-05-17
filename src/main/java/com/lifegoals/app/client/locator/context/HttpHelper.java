package com.lifegoals.app.client.locator.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class HttpHelper {

	/* the gson library will convert the json to objects */
	private static Gson gson = new Gson();
	
	/* converts a json text to a object */
	public static <T> T stringToObject(String json,Class<T> type){
		return gson.fromJson(json, type);
	}
	/* reads the text from a httpurlconnection response */
	public static String readHttpUrlConnectionResponse(HttpURLConnection urlConnection)
			throws IOException {
		InputStream inputStream = urlConnection.getInputStream();
		String responseText = fromStream(inputStream);
		return responseText;
	}

	/* creates a httpurlconnection for our requests. here we are setting the headers */;
	public  static HttpURLConnection createHttpUrlConnection(String path,
			String method, Object body,String token) throws IOException {
		/* create a simple http url connection */
		URL url = new URL(Context.ROOT + path);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod(method);
		httpCon.setRequestProperty("Content-Typeccept", "application/json");
		httpCon.setRequestProperty("Content-Type", "application/json");
		if(token!=null){
			httpCon.setRequestProperty("Token", token);
		}else{
			System.out.println("toke is nul");
		}
		if (body != null) {
			// if we have a body add it
			OutputStreamWriter out = new OutputStreamWriter(
					httpCon.getOutputStream());
			out.write(gson.toJson(body));
			out.close();
		}

		return httpCon;
	}

	/* converts inputstream to string */
	public static String fromStream(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder out = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
			out.append(newLine);
		}
		return out.toString();
	}
}