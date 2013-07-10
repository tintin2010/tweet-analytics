package tweets.analyze;

import java.util.Map;

public abstract class BaseBean {
	public static final String ID = "identifier";
	public abstract String getType();
	public abstract String getId();
	public abstract Map<String, Object> getProperties();
	public abstract String toDetailedString();
	
	@Override
	public String toString() {
			return "{id=" + getId() +" type="+getType()+"}";
	}
}
