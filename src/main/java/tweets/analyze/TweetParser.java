package tweets.analyze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TweetParser {
	private Gson gson_;
	
	public void TweetInjector() {
		gson_= new GsonBuilder().setPrettyPrinting().create();
	}
	
	private int parseTweets(JsonArray tweets, 
			Collection<BaseBean> vertices, 
			Collection<BaseEdge> edges) {
	  int counter = 0;
	
	  for (JsonElement tweet : tweets) {
		JsonObject tweetObj	= tweet.getAsJsonObject();
		UserBean ub = parseUser(tweetObj);
		TweetBean tb = parseTweet(tweetObj, ub);
		//System.out.println(counter + " User Bean ="+ ub);
		//System.out.println(counter + " Tweet Bean:" + tb);
		vertices.add(ub);
		vertices.add(tb);
		edges.add(new UserToTweetEdge(ub, tb));
		
		JsonObject originalTweet = tweetObj.get("retweeted_status") != null ? 
				                   tweetObj.get("retweeted_status").getAsJsonObject() : null;
		
	    if (originalTweet != null) {
			//parse the original tweet
			UserBean ub1 = parseUser(originalTweet);
			TweetBean tb1 = parseTweet(originalTweet, ub1);
			//System.out.println(counter + " Orig User Bean ="+ ub1);
			//System.out.println(counter + " Orig Tweet Bean:" + tb1);
			vertices.add(ub1);
			vertices.add(tb1);
			edges.add(new UserToTweetEdge(ub1, tb1));
			edges.add(new TweetToReTweetOfEdge(tb, tb1));
		}
	    counter++;
	  }
	  
	  return counter-1;
	  }
	  
	private UserBean parseUser(JsonObject tweetObj) {
		if (tweetObj != null) {
			JsonObject user = tweetObj.getAsJsonObject("user");
			if (user != null) {
				UserBean ub = new UserBean(user.get("name").getAsString(), 
						user.get("screen_name").getAsString(), 
						user.get("location").getAsString(),
						user.get("profile_background_image_url").getAsString(),
						user.get("followers_count").getAsInt(),
						user.get("friends_count").getAsInt());
				return ub;
			}
		}
		return null;
	}
	
	private TweetBean parseTweet(JsonObject tweetObj, UserBean user) {
		if (tweetObj != null) {
			JsonObject metadata = tweetObj.getAsJsonObject("metadata");
			if (metadata != null) {
				JsonArray hashtags = tweetObj.get("entities").getAsJsonObject()
						.get("hashtags").getAsJsonArray();
				List<String> hashtagList = new ArrayList();
				for (JsonElement hashtag : hashtags) {
					hashtagList.add(hashtag.getAsJsonObject().get("text").getAsString());
				}

				JsonArray userMentions = tweetObj.get("entities").getAsJsonObject()
						.get("user_mentions").getAsJsonArray();
				List<String> userMentionList = new ArrayList();
				for (JsonElement userMention : userMentions) {
					userMentionList.add(userMention.getAsJsonObject().get("screen_name").getAsString());
				}


				TweetBean tb = new TweetBean(
						metadata.get("iso_language_code").getAsString(),
						tweetObj.get("text").getAsString(),
						tweetObj.get("created_at").getAsString(),
						tweetObj.get("id").getAsString(),
						tweetObj.get("source").getAsString(),
						hashtagList, userMentionList, 
						tweetObj.get("retweeted").getAsBoolean(),
						tweetObj.get("favorited").getAsBoolean(),
						tweetObj.get("retweet_count").getAsInt(),
						tweetObj.get("favorite_count").getAsInt(), 
						user);
				return tb;
			}
		}
		return null;
	}
	
	String parseFileToJson(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new 
					FileReader(new File(filename)));
			String text = "";
			StringBuilder json = new StringBuilder();
			while((text = reader.readLine()) !=null) {
				json.append(text);
			}
	
			return json.toString();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
		
	}
	
	public int parseTweets(String json, 
			                    Collection<BaseBean> vertices,
			                    Collection<BaseEdge> edges) 
			                    throws IOException {
		
		if (vertices == null || edges == null || json == null) {
			throw new RuntimeException("Instantiate the vertices and " +
					"edges and json before parsing tweets");
		}
		
	   JsonObject jobj = gson_.fromJson(json, JsonObject.class);
	   if (jobj != null) {
		   JsonElement statuses = (JsonElement) jobj.get("statuses");
		   if (statuses != null) {
			  JsonArray tweets = statuses.getAsJsonArray();
			  if (tweets != null) {
		         return parseTweets(tweets, vertices, edges);
			  }
	       }
	   }
	   return -1;
	}
	
	public static void main(String[] args) {
		try {
			TweetParser ti = new TweetParser();
			List<BaseBean> vertices = new ArrayList();
			List<BaseEdge> edges = new ArrayList();
			String json = ti.parseFileToJson("./sample-tweets/adele_tweets");
			int numTweets = ti.parseTweets(json, vertices, edges);
			System.out.println(" Number of tweets =" + numTweets);
			System.out.println(" Number of vertices =" + vertices.size());
			System.out.println(" Number of edges ="+ edges.size());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
