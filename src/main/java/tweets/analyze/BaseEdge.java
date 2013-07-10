package tweets.analyze;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseEdge {
  public static final String TIMESTAMP = "time";
  public static final String ID = "identifier";
  public static final String TYPE = "type";
  public abstract String getType();
  public abstract String getId();
  public abstract String getFromVertexId();
  public abstract String getToVertexId();
  public abstract Date getTimeStamp();
  
  public Map<String, Object> getProperties() {
    Map<String, Object> properties = new HashMap();
    properties.put(ID, getId());
    properties.put(TIMESTAMP, getTimeStamp());
    properties.put(TYPE, getType());
	return properties;
  }
  
  @Override
  public String toString() {
		return "{id=" + getId() +" type="+getType()+"}";
  }
}
