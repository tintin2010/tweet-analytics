package tweets.analyze;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseEdge {
  public static final String TIMESTAMP = "time";
  public abstract String getType();
  public abstract String getId();
  public abstract String getFromVertexId();
  public abstract String getToVertexId();
  public abstract Date getTimeStamp();
  
  public Map<String, Object> getProperties() {
    Map<String, Object> properties = new HashMap();
    properties.put(TIMESTAMP, getTimeStamp());
	return properties;
  }
  

}
