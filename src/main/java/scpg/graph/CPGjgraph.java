package scpg.graph;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DirectedPseudograph;

public class CPGjgraph extends DirectedPseudograph<Vertex, CPGEdge> {
    public String filePath;
    public Vertex entry = null;
    public CPGjgraph() {
        super(CPGEdge.class);
        // TODO Auto-generated constructor stub
    }
    public DefaultDirectedGraph<Vertex, CPGEdge> asDefaultDirectedGraph() {
        DefaultDirectedGraph<Vertex, CPGEdge> result = new DefaultDirectedGraph<>(CPGEdge.class);
        Graphs.addAllVertices(result, vertexSet());
        Graphs.addAllEdges(result, this, edgeSet());
        return result;
    }

    public boolean addVertex(Vertex v) {
        if (v == null) {
            throw new NullPointerException();
        } else if (v.getCode().isEmpty()) {
            return false;
        } else if (v.getLocation().equals("unknown")) {
            return false;
        } else {
            boolean added=super.addVertex(v);
            return added;
        }
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Vertex getEntry() {
        return entry;
    }

    public void setEntry(Vertex entry) {
        this.entry = entry;
    }
}
