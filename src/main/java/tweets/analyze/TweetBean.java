package tweets.analyze;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TweetBean extends BaseBean {
   private final String iso_language_code_;
   private final String text_;
   private final Date creation_time_;
   private final String id_;
   private final String source_;
   private final List<String> hashtags_;
   private final List<String> userMentions_;
   private final Boolean reTweeted_;
   private final Boolean favorited_;
   private final Integer retweetCount_;
   private final Integer favoriteCount_;
 
   
   private final static Set<String> properties_ = new HashSet();
   private static final String ISO_LANG_CODE = "iso_language_code";
   private static final String TEXT = "text";
   private static final String SOURCE = "source";
   private static final String HASHTAGS = "hashtags";
   private static final String USERMENTIONS = "usermentions";
   private static final String RETWEETED = "retweeted";
   private static final String NUMBER_RETWEETS = "number_retweets";
   private static final String FAVORITED = "favorited";
   private static final String NUMBER_FAVORITED = "number_favorites";
   private static final String TYPE = "type";
   private static final String TWEET = "tweet";
   private static final String CREATION_TIME = "created_at";

   static {
	  properties_.add(ID);	  
 	  properties_.add(ISO_LANG_CODE);
 	  properties_.add(TEXT);
 	  properties_.add(SOURCE);
 	  properties_.add(HASHTAGS);
 	  properties_.add(USERMENTIONS);
 	  properties_.add(RETWEETED);
 	  properties_.add(NUMBER_RETWEETS);
 	  properties_.add(FAVORITED);
 	  properties_.add(NUMBER_FAVORITED);
	  properties_.add(TYPE);
	  properties_.add(CREATION_TIME);
   };
   
  public TweetBean(String iso_language_code, 
		           String text, 
		           String ts, 
		           String id, 
		           String source, 
		           List<String> hashtags, 
		           List<String> userMentions, 
		           boolean reTweeted, 
		           boolean favorited,
		           int retweetCount,
		           int favoriteCount,
		           UserBean user) {
	  id_ = id;
	  iso_language_code_ = iso_language_code;
	  text_ = text;
	  //TODO : fix it
	  creation_time_ = new Date(ts);
	  source_ = source;
	  hashtags_ = hashtags;
	  userMentions_ = userMentions;
	  reTweeted_ = reTweeted;
	  favorited_ = favorited;
	  retweetCount_ = retweetCount;
	  favoriteCount_ = favoriteCount;
  }
  
  public Date getCreationTime() {
	  return creation_time_;
  }
  
  public String toString() {
	  StringBuilder sb = new StringBuilder();
	  sb.append("id=")
	    .append(id_)
	    .append(" iso_lang_code=")
	    .append(iso_language_code_)
	    .append(" text=")
	    .append(text_)
	    .append(" created at=")
	    .append(creation_time_)
	    .append(" source=")
	    .append(source_)
	    .append(" hashtags=")
	    .append(hashtags_)
	    .append(" usermentions=")
	    .append(userMentions_)
	    .append(" retweeted=")
	    .append(reTweeted_)
	    .append(" retweetcount=")
	    .append(retweetCount_)
	    .append(" favorited=")
	    .append(favorited_)
	    .append(" favoritecount=")
	    .append(favoriteCount_);
	  return sb.toString();
  }

  
@Override
public String getId() {
	return id_;
}

@Override
public Map<String, Object> getProperties() {
	Map<String, Object> properties = new HashMap();
	for (String property : properties_) {
		properties.put(property, getProperty(property));
	}
	return properties;
}

public <T> T getProperty(String propertyName) {
	if (propertyName.equalsIgnoreCase(ID)) {
		return (T) id_;
	} else if (propertyName.equalsIgnoreCase(TEXT)) {
		return (T) text_;
	} else if (propertyName.equalsIgnoreCase(SOURCE)) {
		return (T) source_;
	} else if (propertyName.equalsIgnoreCase(ISO_LANG_CODE)) {
		return (T) iso_language_code_;
	} else if (propertyName.equalsIgnoreCase(NUMBER_RETWEETS)) {
		return (T) retweetCount_;
	} else if (propertyName.equalsIgnoreCase(RETWEETED)) {
		return (T) reTweeted_;
	} else if (propertyName.equalsIgnoreCase(NUMBER_FAVORITED)) {
		return (T) favoriteCount_;
	} else if (propertyName.equalsIgnoreCase(FAVORITED)) {
		return (T) favorited_;
	} else if (propertyName.equalsIgnoreCase(HASHTAGS)) {
		return (T) hashtags_;
	} else if (propertyName.equalsIgnoreCase(USERMENTIONS)) {
		return (T) userMentions_;
	} else if (propertyName.equalsIgnoreCase(TYPE)) {
		return (T) TWEET;
	} else {
		throw new UnsupportedOperationException("Property="+propertyName+" not supported");
	}
}

@Override
public String getType() {
	return TWEET;
}

}
