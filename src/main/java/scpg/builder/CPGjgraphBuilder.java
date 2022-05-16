package scpg.builder;

import de.fraunhofer.aisec.cpg.graph.Graph;
import de.fraunhofer.aisec.cpg.graph.Node;
import de.fraunhofer.aisec.cpg.graph.edge.PropertyEdge;
import de.fraunhofer.aisec.cpg.graph.edge.Properties;

import scala.collection.mutable.MutableList;
import scala.collection.JavaConverters;

import scpg.graph.CPGEdge;
import scpg.graph.CPGjgraph;
import scpg.graph.Vertex;

import java.util.*;

public class CPGjgraphBuilder {
    private final Graph originCPG;
    private CPGjgraph cpg;
    public CPGjgraphBuilder(Graph originCPG) {
        this.originCPG=originCPG;
        cpg = new CPGjgraph();
        cpg.setFilePath(originCPG.getNodes().get(0).getFile());
    }

    public void build() {
        Map<String,Vertex> allVertexs = new LinkedHashMap<>();
        Map<Node,String> ctrlNodes = new LinkedHashMap<>();
//        Map<String,Node> dataNodes = new LinkedHashMap<>();
        Set<PropertyEdge<Node>> allCFEdges = new HashSet<>();
        int nodeCounter = 1;
        Iterator<Node> origNode = nodeIterator(originCPG);
        while(origNode.hasNext()){
            Node node = origNode.next();
            Vertex v =new Vertex(node);
            v.setId(nodeCounter);
            boolean added=cpg.addVertex(v);
            if(added == false){
                continue;
            }
            nodeCounter++;
            String name = "v"+String.valueOf(nodeCounter);
            ctrlNodes.put(node, name);
            allVertexs.put(name,v);

            List<PropertyEdge<Node>> prevEOGEdges = node.getPrevEOGEdges();
            List<PropertyEdge<Node>> nextEOGEdges = node.getNextEOGEdges();
            allCFEdges.addAll(prevEOGEdges);
            allCFEdges.addAll(nextEOGEdges);
            //抽象语法树
//            Iterator<Node> astChildren=node.getAstChildren().iterator();
//            while (astChildren.hasNext()) {
//                Node astNode = astChildren.next();
//                Vertex astVertex = new Vertex(astNode);
//                if(cpg.addVertex(astVertex)==true){
//                    CPGEdge edge = new CPGEdge(CPGEdge.Type.AST);
//                    edge.setStartAndEnd(v, astVertex);
//                    edge.setId(Objects.hash(node, astNode, "AST"));
//                    cpg.addEdge(v, astVertex, edge);
//                }
//            }
            //数据流边
            Iterator<Node> prevDFGs=node.getPrevDFG().iterator();
            Iterator<Node> nextDFGs=node.getNextDFG().iterator();
            //INCOMING
            while (prevDFGs.hasNext()) {
                Node prevDFG= prevDFGs.next();
                Vertex prevDFGv = new Vertex(prevDFG);
                if(node.equals(prevDFG)){
                    continue;
                }
                if(cpg.addVertex(prevDFGv)) {
                    CPGEdge edge = new CPGEdge(CPGEdge.Type.DDG_U);
                    edge.setStartAndEnd(v, prevDFGv);
                    edge.setId(Objects.hash(node, prevDFG));
                    cpg.addEdge(v, prevDFGv, edge);
                }
            }
            //OUTGOING
            while (nextDFGs.hasNext()) {
                Node nextDFG= nextDFGs.next();
                Vertex nextDFGv = new Vertex(nextDFG);
                if(cpg.addVertex(nextDFGv)) {
                    CPGEdge edge = new CPGEdge(CPGEdge.Type.DDG_D);
                    edge.setStartAndEnd(v, nextDFGv);
                    edge.setId(Objects.hash(node, nextDFG));
                    cpg.addEdge(v, nextDFGv, edge);
                }
            }



        }
        //控制流边
        Iterator<PropertyEdge<Node>> allEdgeIterator = allCFEdges.iterator();
        Set<Integer> edgeIDs = new HashSet<>();
        while (allEdgeIterator.hasNext()) {
            PropertyEdge<Node> eogEdge=allEdgeIterator.next();
            if(edgeIDs.contains(eogEdge.hashCode())){//去重判断
                continue;
            }else{
                edgeIDs.add(eogEdge.hashCode());
            }
            Vertex start= allVertexs.get(ctrlNodes.get(eogEdge.getStart()));
            Vertex end= allVertexs.get(ctrlNodes.get(eogEdge.getEnd()));
            CPGEdge edge = new CPGEdge(CPGEdge.Type.CFG);
            edge.setStartAndEnd(start,end);
            edge.setId(eogEdge.hashCode());
            edge.setName((String) eogEdge.getProperty(Properties.NAME));
            edge.setPropertyEdge(eogEdge);
            cpg.addEdge(start, end, edge);
        }


    }
    public Iterator<Node> nodeIterator(Graph graph) {
        return Collections.unmodifiableSet(new HashSet(graph.getNodes())).iterator();
    }
    public Iterator<PropertyEdge<Node>> propertyEdgeIterator(MutableList<PropertyEdge<Node>> EOGEdges) {
        return JavaConverters.asJavaIterator(EOGEdges.iterator());
    }
    public CPGjgraph getCpg() {
        return cpg;
    }
}
