package tweets.analyze;

import java.util.Date;
import java.util.Map;

public final class UserToTweetEdge extends BaseEdge {
    private final String userId_;
    private final String tweetId_;
    private final Date tweetTimeStamp_;
    
	public UserToTweetEdge(UserBean ub, TweetBean tb) {
	   userId_ = ub.getId();
	   tweetId_ = tb.getId();
	   tweetTimeStamp_ = tb.getCreationTime();
	}
	
	@Override
	public String getType() {
		return "TWEETED";
	}

	@Override
	public String getId() {
		return userId_ + ":" + tweetId_;
	}

	@Override
	public Date getTimeStamp() {
		return tweetTimeStamp_;
	}

	@Override
	public String getFromVertexId() {
		return userId_;
	}

	@Override
	public String getToVertexId() {
		return tweetId_;
	}
	
}
