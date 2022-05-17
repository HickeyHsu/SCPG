package scpg.graph;

import de.fraunhofer.aisec.cpg.graph.Node;
import de.fraunhofer.aisec.cpg.graph.declarations.TranslationUnitDeclaration;
import de.fraunhofer.aisec.cpg.graph.declarations.TypedefDeclaration;
import de.fraunhofer.aisec.cpg.sarif.PhysicalLocation;
import static de.fraunhofer.aisec.cpg.graph.GraphKt.getLabels;

import org.parboiled.common.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Vertex extends AbstractVertex {
    private long id;
    public String name;
    private String code;
    private String comment;
    private String file;
    private List<CPGEdge> prevEOGEdges;
    private List<CPGEdge> nextEOGEdges;
    private List<Vertex> astChildren;
    private List<Vertex> prevEOG;
    private List<Vertex> nextEOG;
    private Set<Node> prevDFG;
    private Set<Node> nextDFG;
    private Set<TypedefDeclaration> typedefs;
    private boolean isInferred = false;
    private boolean isImplicit = false;
    private int argumentIndex = 0;
    private int line;
    private String location;
    private String type;
    private String building;
    private List<Vertex> astVertices=new ArrayList<>();
    private List<String> annotations=new ArrayList<>();
    private List<String> label = new ArrayList<>();
    public Vertex(Node node) {
        this.name= StringUtils.escape(node.getName());
        this.code= StringUtils.escape(node.getCode());
        this.comment= StringUtils.escape(node.getComment());
        this.file= StringUtils.escape(node.getFile());
        this.isInferred= node.isInferred();
        this.isImplicit= node.isImplicit();
        this.argumentIndex= node.getArgumentIndex();
        this.typedefs=node.getTypedefs();
        this.location=PhysicalLocation.locationLink(node.getLocation());
//        List<Annotation> nodeAnnotations=node.getAnnotations();
//        for(Annotation annot:nodeAnnotations){
//            annotations.add(annot.toString());
//        }
        this.label=getLabels(node);
        this.label.remove("Node");
        this.type=this.label.get(0);
        if(location=="unknown"){
            this.line=0;
        }else{
            this.line=node.getLocation().getRegion().getStartLine();
        }

        if(node.getClass().equals(TranslationUnitDeclaration.class)){
            this.code=this.file;
        }

    }
    public void addAstChild(Vertex astV){
        astVertices.add(astV);
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<CPGEdge> getPrevEOGEdges() {
        return prevEOGEdges;
    }

    public void setPrevEOGEdges(List<CPGEdge> prevEOGEdges) {
        this.prevEOGEdges = prevEOGEdges;
    }

    public List<CPGEdge> getNextEOGEdges() {
        return nextEOGEdges;
    }

    public void setNextEOGEdges(List<CPGEdge> nextEOGEdges) {
        this.nextEOGEdges = nextEOGEdges;
    }

    public List<Vertex> getAstChildren() {
        return astChildren;
    }

    public void setAstChildren(List<Vertex> astChildren) {
        this.astChildren = astChildren;
    }

    public List<Vertex> getPrevEOG() {
        return prevEOG;
    }

    public void setPrevEOG(List<Vertex> prevEOG) {
        this.prevEOG = prevEOG;
    }

    public List<Vertex> getNextEOG() {
        return nextEOG;
    }

    public void setNextEOG(List<Vertex> nextEOG) {
        this.nextEOG = nextEOG;
    }

    public Set<Node> getPrevDFG() {
        return prevDFG;
    }

    public void setPrevDFG(Set<Node> prevDFG) {
        this.prevDFG = prevDFG;
    }

    public Set<Node> getNextDFG() {
        return nextDFG;
    }

    public void setNextDFG(Set<Node> nextDFG) {
        this.nextDFG = nextDFG;
    }

    public List<Vertex> getAstVertices() {
        return astVertices;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Set<TypedefDeclaration> getTypedefs() {
        return typedefs;
    }

    public void setTypedefs(Set<TypedefDeclaration> typedefs) {
        this.typedefs = typedefs;
    }

    public boolean isInferred() {
        return isInferred;
    }

    public void setInferred(boolean inferred) {
        isInferred = inferred;
    }

    public boolean isImplicit() {
        return isImplicit;
    }

    public void setImplicit(boolean implicit) {
        isImplicit = implicit;
    }

    public int getArgumentIndex() {
        return argumentIndex;
    }

    public void setArgumentIndex(int argumentIndex) {
        this.argumentIndex = argumentIndex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public List<String> getAnnotations() {
        return annotations;
    }
}
