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
        Map<Integer,Vertex> allVertexs = new LinkedHashMap<>();
        Map<Node,Integer> ctrlNodes = new LinkedHashMap<>();
//        Map<String,Node> dataNodes = new LinkedHashMap<>();
        Set<PropertyEdge<Node>> allCFEdges = new HashSet<>();

        Iterator<Node> origNode = nodeIterator(originCPG);
        while(origNode.hasNext()){
            Node node = origNode.next();
            int id=node.hashCode();
            Vertex v= genVertex(node,cpg,allVertexs,"CFG");
            if(v==null)continue;

            ctrlNodes.put(node, id);
            List<PropertyEdge<Node>> prevEOGEdges = node.getPrevEOGEdges();
            List<PropertyEdge<Node>> nextEOGEdges = node.getNextEOGEdges();
            allCFEdges.addAll(prevEOGEdges);
            allCFEdges.addAll(nextEOGEdges);
            //抽象语法树AST
            Iterator<Node> astChildren=node.getAstChildren().iterator();
            while (astChildren.hasNext()) {
                Node astNode = astChildren.next();
                Vertex astVertex=genVertex(astNode,cpg,allVertexs,"AST");
                if(astVertex==null)continue;

                v.addAstChild(astVertex);
                CPGEdge edge = new CPGEdge(CPGEdge.Type.AST);
                edge.setStartAndEnd(v, astVertex);
                edge.setId(Objects.hash(v, astVertex, "AST"));
                cpg.addEdge(v, astVertex, edge);
            }
            //数据流边DF
            Iterator<Node> prevDFGs=node.getPrevDFG().iterator();
            Iterator<Node> nextDFGs=node.getNextDFG().iterator();
//            //INCOMING
//            while (prevDFGs.hasNext()) {
//                Node prevDFG= prevDFGs.next();
//                Vertex prevDFGv=genVertex(prevDFG,cpg,allVertexs,"DDG_U");
//                if(prevDFGv==null)continue;
//
//                CPGEdge edge = new CPGEdge(CPGEdge.Type.DDG_U);
//                edge.setStartAndEnd(v, prevDFGv);
//                edge.setId(Objects.hash(v, prevDFGv));
//                cpg.addEdge(v, prevDFGv, edge);
//            }
            //outgoing
            while (nextDFGs.hasNext()) {
                Node nextDFG= nextDFGs.next();
                Vertex nextDFGv=genVertex(nextDFG,cpg,allVertexs,"DDG_D");
                if(nextDFGv==null)continue;

                CPGEdge edge = new CPGEdge(CPGEdge.Type.DDG_D);
                edge.setStartAndEnd(v, nextDFGv);
                edge.setId(Objects.hash(v, nextDFGv));
                cpg.addEdge(v, nextDFGv, edge);
            }
        }
        //控制流边CF
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
            if(start==null||end==null) continue;
            CPGEdge edge = new CPGEdge(CPGEdge.Type.CFG);
            edge.setStartAndEnd(start,end);
            edge.setId(eogEdge.hashCode());
            edge.setName((String) eogEdge.getProperty(Properties.NAME));
            edge.setPropertyEdge(eogEdge);
            try{
                cpg.addEdge(start, end, edge);
            }catch(Exception e){
                print(start);
                print(end);
                throw e;
            }

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
    private void print(Object o){
        System.out.println(o.toString());
    }
    private Vertex genVertex(Node node,CPGjgraph cpg,Map<Integer,Vertex> allVertexs,String building){
        Vertex v;
        int id=node.hashCode();
        if(allVertexs.containsKey(id)){
            v= allVertexs.get(id);
//            if (building=="CFG") v.setBuilding(building);
        }else{
            v = new Vertex(node);
            v.setId(id);
            v.setBuilding(building);
            if(cpg.addVertex(v)==false)return null;
            allVertexs.put(id,v);
        }
        return v;
    }
}
