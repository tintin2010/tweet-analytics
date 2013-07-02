package tweets.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class TweetInjecter {
    public static void parseTweetsJson() throws IOException {
    	   try {
			BufferedReader reader = new BufferedReader(new 
					FileReader(new File("./sample-tweets/adele_tweets")));
			String text = "";
			StringBuilder json = new StringBuilder();
			while((text = reader.readLine()) !=null) {
				json.append(text);
			}
			
			System.out.println("Got jsonString="+json.toString());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject jobj = gson.fromJson(json.toString(), JsonObject.class);
			System.out.println("Got json obj="+gson.toJson(jobj));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
	
    public static void curlTweets() {
	    System.out.println(TwitterAccount.getCurlUrl());
	    Process curlProc;
	    try {
	        curlProc = Runtime.getRuntime().exec(TwitterAccount.getCurlUrl());
	        BufferedReader curlIn = new BufferedReader(new 
	        		InputStreamReader(curlProc.getInputStream()));
	        BufferedWriter writer = new BufferedWriter(new 
	        		FileWriter(new File("./tweets")));
	        String text = "";
	        
	        while ((text = curlIn.readLine()) != null) {
	            writer.write(text+"\n");
	        }

	        curlIn.close();
	        writer.close();
	        
	    } catch (IOException e1) {
	        e1.printStackTrace();
	    }
	}
	
	
	private static class TwitterAccount {
		private final static String api = 
				"https://api.twitter.com/1.1/search/tweets.json?q=taylorswift";
		private final static String oauth_consumer_key= 
				"cMyiN8wl2p2G9xiJTisItw";
		private final static String oauth_nonce = 
				"e44bed036da06e15c08d767fa3ca7b63";
		private final static String oauth_signature=
				"6TH9sHEc%2FcF44PHX4tsS8Z8a4mQ%3D";
		private final static String oauth_signature_method="HMAC-SHA1";
		//private final static String oauth_timestamp="1372748552";
		private final static String oauth_timestamp=Long.toString(System.currentTimeMillis()/1000L);
		
		private final static String oauth_token=
				"31171660-q1SiS6fTROlGkdXzIZnTasQmtrcIhpLcXOm4UCeat";
		private final static String oauth_version="1.0";
		
		public static String fetchOAuthUrl() {
			StringBuilder sb = new StringBuilder();
			sb.append("Authorization: OAuth oauth_consumer_key=\"")
			  .append(oauth_consumer_key)
			  .append("\",oauth_nonce=\"")
			  .append(oauth_nonce)
			  .append("\",oauth_signature=\"")
			  .append(oauth_signature)
			  .append("\",oauth_signature_method=\"")
			  .append(oauth_signature_method)
			  .append("\",oauth_timestamp=\"")
			  .append(oauth_timestamp)
			  .append("\",oauth_token=\"")
			  .append(oauth_token)
			  .append("\",oauth_version=\"")
			  .append(oauth_version)
			  .append("\"");
			return sb.toString();
		}
		
		public static String getCurlUrl() {
			StringBuilder sb = new StringBuilder();
			sb.append("curl --get '")
			  .append(api).append("'")
			  .append(" --header '")
			  .append(fetchOAuthUrl()).append("'");
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		try {
			parseTweetsJson();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
