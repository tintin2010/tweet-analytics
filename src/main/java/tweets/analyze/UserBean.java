package tweets.analyze;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class UserBean extends BaseBean {
  private static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
  private static final String NUM_FRIENDS = "NUM_FRIENDS";
  private static final String NUM_FOLLOWERS = "NUM_FOLLOWERS";
  private static final String LOCATION = "LOCATION";
  private static final String SCREEN_NAME = "SCREEN_NAME";
  private static final String NAME = "NAME";
  private static final String TYPE = "type";
  private static final String USER = "user";
  
  private String name_;
  private String screenName_;
  private String location_;
  private Integer numFollowers_;
  private Integer numFriends_;
  private String profileImageurl_;
  
  private final static Set<String> properties_ = new HashSet();


  static {
	  properties_.add(NAME);
	  properties_.add(SCREEN_NAME);
	  properties_.add(LOCATION);
	  properties_.add(NUM_FOLLOWERS);
	  properties_.add(NUM_FRIENDS);
	  properties_.add(PROFILE_IMAGE_URL);
	  properties_.add(TYPE);
	  properties_.add(ID);
  };
  
  public UserBean(String name, 
		           String screenName, 
		           String location,
		           String imageurl,
		           int followers,
		           int friends) {
	  setProperty(NAME, name);
	  setProperty(SCREEN_NAME, screenName);
	  setProperty(LOCATION, location);
	  setProperty(PROFILE_IMAGE_URL, imageurl);
	  setProperty(NUM_FOLLOWERS, followers);
	  setProperty(NUM_FRIENDS, friends);
  }
  
  @Override
  public String toDetailedString() {
	  StringBuilder sb = new StringBuilder();
	  sb.append("name=")
	    .append(name_)
	    .append(" screename=")
	    .append(screenName_)
	    .append(" location=")
	    .append(location_)
	    .append(" num_followers=")
	    .append(numFollowers_)
	    .append(" num_friends=")
	    .append(numFriends_)
	    .append(" profile_img=")
	    .append(profileImageurl_);
	  return sb.toString();
  }


public <T> T getProperty(String propertyName) {
	if (propertyName.equalsIgnoreCase(ID)) {
		return (T) getId();
	} else if (propertyName.equalsIgnoreCase(NAME)) {
		return (T) name_;
	} else if (propertyName.equalsIgnoreCase(SCREEN_NAME)) {
		return (T) screenName_;
	} else if (propertyName.equalsIgnoreCase(LOCATION)) {
		return (T) location_;
	} else if (propertyName.equalsIgnoreCase(NUM_FOLLOWERS)) {
		return (T) numFollowers_;
	} else if (propertyName.equalsIgnoreCase(NUM_FRIENDS)) {
		return (T) numFriends_;
	} else if (propertyName.equalsIgnoreCase(PROFILE_IMAGE_URL)) {
		return (T) profileImageurl_;
	} else if (propertyName.equalsIgnoreCase(TYPE)) {
		return (T) USER;
	} else {
		throw new UnsupportedOperationException("Property="+propertyName+" not supported");
	}
}


public void setProperty(String propertyName, Object propertyVal) {
	if (propertyName.equalsIgnoreCase(NAME)) {
		setName((String) propertyVal);
	} else if (propertyName.equalsIgnoreCase(SCREEN_NAME)) {
		setScreenName((String) propertyVal);
	} else if (propertyName.equalsIgnoreCase(LOCATION)) {
		setLocation((String) propertyVal);
	} else if (propertyName.equalsIgnoreCase(NUM_FOLLOWERS)) {
		setNumFollowers((Integer) propertyVal);
	} else if (propertyName.equalsIgnoreCase(NUM_FRIENDS)) {
		setNumFriends((Integer) propertyVal);
	} else if (propertyName.equalsIgnoreCase(PROFILE_IMAGE_URL))  {
		setProfileImageUrl((String) propertyVal);
	} else {
		throw new UnsupportedOperationException("Property="+propertyName+" not supported");
	}
}

private void setNumFriends(Integer numFriends) {
	numFriends_ = numFriends;
}

private void setNumFollowers(Integer numFollowers) {
    numFollowers_ = numFollowers;	
}

private void setLocation(String location) {
	location_ = location;
}

private void setScreenName(String screenName) {
	screenName_ = screenName;
}

private void setName(String name) {
	name_ = name;
}

public String getName() {
	return name_;
}

public String getScreenName() {
	return screenName_;
}

private void setProfileImageUrl(String imageurl) {
  profileImageurl_ = imageurl;
}

@Override
public boolean equals(Object that) {
	UserBean thatUser = (UserBean) that;
	return this.name_.equalsIgnoreCase(thatUser.getName()) && 
		   this.screenName_.equalsIgnoreCase(thatUser.getScreenName());
}

@Override
public int hashCode() {
	return this.name_.hashCode() + 117 * this.screenName_.hashCode();
}

@Override
public Map<String, Object> getProperties() {
	Map<String, Object> properties = new HashMap();
	for (String property : properties_) {
		properties.put(property, getProperty(property));
	}
	return properties;
}

@Override
public String getType() {
	return USER;
}

@Override
public String getId() {
	return screenName_;
}

}
