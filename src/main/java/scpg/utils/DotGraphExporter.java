package scpg.utils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.ExportException;
import org.jgrapht.nio.dot.DOTExporter;
import scpg.graph.CPGEdge;
import scpg.graph.Vertex;


public class DotGraphExporter {

    public static void exportAsDot(
            final DefaultDirectedGraph<Vertex,CPGEdge> graph, final String path, final String fileName
    ){
        try {

            final String filePath = path + "/" + fileName + ".dot";
            final File dotFile = new File(filePath);
            DOTExporter<Vertex,CPGEdge> exporter = getDOTExporter(graph);
            exporter.exportGraph(graph, dotFile);
            System.out.println("dot file exported at: "+dotFile.getAbsolutePath());

            final Graphviz gv = new Graphviz();
            gv.readSource(filePath);
            final String type = "png";
            final String repesentationType = "dot";
            final File out = new File(path + "/" + fileName + "." + type);
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type, repesentationType), out);
            System.out.println("Graph exported at: "+out.getAbsolutePath());
//            dotFile.delete();
        } catch (final ExportException e) {
            e.printStackTrace();
        }
    }
    protected static DOTExporter<Vertex,CPGEdge> getDOTExporter(DefaultDirectedGraph<Vertex,CPGEdge> graph) {
        DOTExporter<Vertex,CPGEdge> dot = new DOTExporter<>();
        dot.setVertexAttributeProvider(n ->getComponentAttributes(n));
        dot.setEdgeIdProvider(e -> e.toString().replaceAll("\"", "'").toLowerCase());
        dot.setEdgeAttributeProvider(e ->getComponentAttributes(e));
        return dot;
    }
    public static Map<String, Attribute> getComponentAttributes(final Vertex component) {
        final Map<String, Attribute> result = new HashMap<>();
        final Attribute fillColor = DefaultAttribute.createAttribute("white");
        result.put("location", DefaultAttribute.createAttribute(String.valueOf(component.getLocation())));
//        result.put("label", DefaultAttribute.createAttribute(component.getLocation()+":"+component.getCode()));
        result.put("label", DefaultAttribute.createAttribute(component.getLine()+":"+component.getCode()));
//        result.put("label", DefaultAttribute.createAttribute(component.getLabel().toString()));
//        result.put("name", DefaultAttribute.createAttribute(component.getName()));
        result.put("type", DefaultAttribute.createAttribute(component.getType()));
        result.put("id", DefaultAttribute.createAttribute(component.getId()));
        result.put("line", DefaultAttribute.createAttribute(component.getLine()));
//        result.put("annotations", DefaultAttribute.createAttribute(component.getAnnotations().toString()));
        result.put("code", DefaultAttribute.createAttribute(component.getCode()));
        result.put("building", DefaultAttribute.createAttribute(component.getBuilding()));
        result.put("style", DefaultAttribute.createAttribute("filled"));
        if (component.getBuilding()=="CFG") {
            result.put("fillcolor", DefaultAttribute.createAttribute("yellow"));
        }else {
            result.put("fillcolor", fillColor);
        }
        if (component.getType() != null) {
            switch (component.getType()) {
                default:
                    result.put("fontname", DefaultAttribute.createAttribute("helvetica"));
                    result.put("shape", DefaultAttribute.createAttribute("oval"));
                    break;
            }
        }
        return result;
    }
    public static Map<String, Attribute> getComponentAttributes(final CPGEdge component) {
        final Map<String, Attribute> result = new HashMap<>();
        if (component.getType() != null) {
            result.put("edgeType", DefaultAttribute.createAttribute(component.getType().label));
            switch (component.getType()) {
                case CFG:
                    result.put("splines", DefaultAttribute.createAttribute(true));
                    result.put("color", DefaultAttribute.createAttribute("green"));

                    break;
                case DDG_D:
                    result.put("style", DefaultAttribute.createAttribute("dashed"));
                    result.put("color", DefaultAttribute.createAttribute("blue"));
                    break;
                case DDG_U:
                    result.put("style", DefaultAttribute.createAttribute("dashed"));
                    result.put("color", DefaultAttribute.createAttribute("orange"));
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
