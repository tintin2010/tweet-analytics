package tweets.analyze;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class GremlinTitanTweetGraph {
	private static final Object TITAN_DB_PROPERTIES_FILE = "propertiesfile";
	private TitanGraph graph_;
	private List<String> keyIndices_;
	
	private void init() {
		Map<String, Object> titandbConfig = ConfigLoader.getConfig(ConfigLoader.TITAN_DB);
		graph_ = TitanFactory.open((String) titandbConfig.get(TITAN_DB_PROPERTIES_FILE));
	}
	
    public GremlinTitanTweetGraph(List<String> keyIndices, boolean restart) {
	   init();
	   keyIndices_ = new ArrayList();
	   Set<String> preExistingKeyIndices = graph_.getIndexedKeys(Vertex.class);
	   
	   if (restart) {
           for (String keyIndex : preExistingKeyIndices) {
        	     graph_.dropKeyIndex(keyIndex, Vertex.class);
           }
	   }
	   
	   for (String keyIndex : keyIndices) {
		 if (!preExistingKeyIndices.contains(keyIndex)) {
		   graph_.createKeyIndex(keyIndex, Vertex.class);
		   keyIndices_.add(keyIndex);
	     }
	   }
	   
	   System.out.println("Current set of KEY indices on vertex="+keyIndices_);
	  }
    
    public void addVertex(Collection<BaseBean> beans, boolean restart) {
    	    if (restart) {
    	    	   for(Vertex v : graph_.getVertices()) {
    	    		   graph_.removeVertex(v);
    	    	   }
    	    }
    	    
    	    for (BaseBean bean : beans) {
    	    	  Vertex b = graph_.addVertex(null);
    	    	  Map<String, Object> properties = bean.getProperties();
    	    	  for (Entry<String, Object> entry : properties.entrySet()) {
    	    	    	b.setProperty(entry.getKey(), entry.getValue());
    	    	  }
    	    }
    	    
    	    int counter = 0;
    	    for (Vertex v : graph_.getVertices()) {
    	    	   System.out.println("Added " + counter +" Vertex with id="+v.getProperty(BaseBean.ID));
    	    	   counter++;
    	    }
    }
    
    public void addEdge(String keyIndex, Collection <BaseEdge> edges, boolean restart) {
    	   if (restart) {
	    	   for(Edge e : graph_.getEdges()) {
	    		   graph_.removeEdge(e);
	    	   }
	   }
    	
    	   for (String key : keyIndices_) {
    		   if (key.equalsIgnoreCase(keyIndex)) {
    	         for (BaseEdge edge : edges) {
    	    	       String fromVertextId = edge.getFromVertexId();
    	    	       String toVertextId = edge.getToVertexId();
    	    	       Vertex fromV = graph_.getVertices(key, fromVertextId).iterator().next();
    	    	       Vertex toV = graph_.getVertices(key, toVertextId).iterator().next();
    	    	       if (fromV != null && toV != null) {
    	    	    	      Edge e = graph_.addEdge(null, fromV, toV, edge.getType());
    	    	    	      for (Entry<String, Object> entry : edge.getProperties().entrySet()) {
    	    	    	    	     e.setProperty(entry.getKey(), entry.getValue());
    	    	    	      }
    	    	       }
    	    	       
    	         }
    		   } else {
    			   throw new RuntimeException("KeyIndex="+keyIndex+" not known to the graph, " +
    			   		"cannot search for vertices");
    		   }
    	   }
    	   
    	   int counter = 0;
    	   for (Edge e : graph_.getEdges()) {
    		   System.out.println("Added " + counter +" Edge with id="+ e.getProperty(BaseEdge.ID));
	    	   counter++;
    	   }
    }
    
   public static void main(String[] args) throws IOException {
		TweetParser tp = new TweetParser();
		List<BaseBean> vertices = new ArrayList();
		List<BaseEdge> edges = new ArrayList();
		String json = tp.parseFileToJson("./sample-tweets/adele_tweets");
		int numTweets = tp.parseTweets(json, vertices, edges);
		System.out.println(" Number of tweets =" + numTweets);
		System.out.println(" Number of vertices =" + vertices.size());
		System.out.println(" Number of edges ="+ edges.size());
		
		List<String> keyIndices = new ArrayList();
		keyIndices.add(BaseBean.ID);
		
		GremlinTitanTweetGraph graph = new GremlinTitanTweetGraph(keyIndices, true);
		graph.addVertex(vertices, true);
	    graph.addEdge(BaseBean.ID, edges, true);
		
		graph.graph_.commit();
}


}
