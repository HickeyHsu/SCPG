package scpg.graph;
import de.fraunhofer.aisec.cpg.graph.Node;
import de.fraunhofer.aisec.cpg.graph.edge.PropertyEdge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CPGEdge extends AbstractEdge{
    private static final long serialVersionUID = 5811583473376562089L;
    private long id;
    private Vertex start;
    private Vertex end;
    private String name;
    public final Type type;

    private PropertyEdge<Node> propertyEdge;
    public CPGEdge(Type type){
        this.type = type;
    }

    public enum Type {
        FLOW("Flows"),
        THROWS("Throws"),
        CALLS("Call"),
        RETURN("Return"),
        IS_FUNCTION_OF_AST("IS_FUNCTION_OF_AST"),
        IS_FUNCTION_OF_CFG("IS_FUNCTION_OF_CFG"),
        IS_AST_OF_AST_ROOT("IS_AST_OF_AST_ROOT"),
        IS_AST_PARENT("IS_AST_PARENT"),
        IS_CFG_OF_CFG_ROOT("IS_CFG_OF_CFG_ROOT"),
        FLOWS_TO("FLOWS_TO"),
        FLOWS_TO_TRUE("FLOWS_TO_TRUE"),
        FLOWS_TO_FALSE("FLOWS_TO_FALSE"),
        IS_CLASS_OF("IS_CLASS_OF"),
        DDG_D ("DDG_D"),
        DDG_U ("DDG_U"),
        SELF_FLOW("SELF_FLOW"),
        AST("AST"),
        CFG("CFG"),
        CDG_TRUE("CDG_TRUE"),
        CDG_FALSE("CDG_FALSE"),
        CDG_EPSILON("CDG_EPSILON"),
        CDG_THROWS("CDG_THROWS"),
        CDG_NOT_THROWS("CDG_NOT_THROWS"),
        IMPORTS("IMPORTS");

        public final String label;

        Type(String lbl) {
            this.label = lbl;
        }

        public String toString() {
            return this.label;
        }
        private static final Map<String, Type> MAP = new HashMap<>();
        private static final Set<Type> typeSet = new HashSet<Type>();
        static {
            for (Type type : values()) {
                MAP.put(type.label, type);
                typeSet.add(type);
            }
        }
        public static Type getType(String lbl) {
            return MAP.get(lbl);
        }
        public static Set<Type> getTypeSet(){
            return typeSet;
        }

    }

    public PropertyEdge<Node> getPropertyEdge() {
        return propertyEdge;
    }

    public void setPropertyEdge(PropertyEdge<Node> propertyEdge) {
        this.propertyEdge = propertyEdge;
    }

    public void setStartAndEnd(Vertex start,Vertex end) {
        this.start = start;
        this.end = end;
    }

    public Type getType() {
        return type;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
