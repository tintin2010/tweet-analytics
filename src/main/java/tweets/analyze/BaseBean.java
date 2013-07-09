package tweets.analyze;


import java.util.Map;

public abstract class BaseBean {
	public static final String ID = "id";
	public abstract String getType();
	public abstract String getId();
	public abstract Map<String, Object> getProperties();
}
