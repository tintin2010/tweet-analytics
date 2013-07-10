package tweets.analyze;

import java.util.Date;

public final class TweetToReTweetOfEdge extends BaseEdge {
    private final String tweetId_;
    private final String reTweetOfId_;
    private final Date timeStamp_;
    
	public TweetToReTweetOfEdge(TweetBean tb, TweetBean reTweetOf) {
	   tweetId_ = tb.getId();
	   reTweetOfId_ = reTweetOf.getId();
	   timeStamp_ = tb.getCreationTime();
	}
	
	@Override
	public String getType() {
		return "RETWEETOF";
	}

	@Override
	public String getId() {
		return tweetId_ + ":" + reTweetOfId_;
	}

	@Override
	public Date getTimeStamp() {
		return timeStamp_;
	}

	@Override
	public String getFromVertexId() {
		return tweetId_;
	}

	@Override
	public String getToVertexId() {
		return reTweetOfId_;
	}
}
