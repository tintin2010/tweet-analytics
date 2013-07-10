package tweets.analyze;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

public class ConfigLoader {
	private static final String TWEETS_ANALYZE_YML = "tweets.analyze.yml";
	private static final Logger LOG = Logger.getLogger(ConfigLoader.class.getName());
	public static final String TITAN_DB = "titandb";
	private static Boolean loaded = Boolean.FALSE;
	private static Map config = null;

	private static void printCP() {
		StringBuilder buffer = new StringBuilder();
		for (URL url :
		    ((URLClassLoader) (Thread.currentThread()
		        .getContextClassLoader())).getURLs()) {
		  buffer.append(new File(url.getPath()));
		  buffer.append(System.getProperty("path.separator"));
		}
		
		String classpath = buffer.toString();
		int toIndex = classpath
		    .lastIndexOf(System.getProperty("path.separator"));
		classpath = classpath.substring(0, toIndex);
		LOG.log(Level.FINE,"classpath="+classpath);
	}
	
	private static void loadConfig() {
		printCP();
		URLClassLoader urlClassLoader = (URLClassLoader) (Thread.currentThread().getContextClassLoader()); 
		URL file = urlClassLoader.findResource(TWEETS_ANALYZE_YML);
		if (file == null) {
			LOG.log(Level.SEVERE, "Could not find the yml config file:tweets.analyze.yml");
			throw new RuntimeException("Could not find the yml config file:tweets.analyze.yml");
		}
		LOG.log(Level.FINE,"Found url for " + TWEETS_ANALYZE_YML + file);
		try {
			InputStream input = file.openStream();
			Yaml yaml = new Yaml();
			Iterator<Object> iter = yaml.loadAll(input).iterator();
			while (iter.hasNext()) {
			  Object configObj = iter.next();
			  config = (Map<String, Object>) configObj;
			}
		} catch(IOException ex) {
			LOG.log(Level.SEVERE, "Failed to read " + TWEETS_ANALYZE_YML);
			throw new RuntimeException("Failed to read " + TWEETS_ANALYZE_YML);
		}
	}

	public static Map<String, Object> getConfig(String type) {
		if (!loaded) {
			loadConfig(); 
			loaded = Boolean.TRUE;
		}
		

		if (config != null) {
			if (type.equalsIgnoreCase(TITAN_DB)) {
				Map<String, Object> dbConfig = (Map<String, Object>) config.get(TITAN_DB);
				return dbConfig;
			}
		}
		return null;
	}
}
