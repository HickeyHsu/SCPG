package scpg;
import de.fraunhofer.aisec.cpg.TranslationConfiguration;
import de.fraunhofer.aisec.cpg.TranslationManager;
import de.fraunhofer.aisec.cpg.TranslationResult;
import static de.fraunhofer.aisec.cpg.graph.GraphKt.getGraph;




import de.fraunhofer.aisec.cpg.graph.Graph;
import scpg.builder.CPGjgraphBuilder;
import scpg.graph.CPGjgraph;
import scpg.utils.DotGraphExporter;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Generate{
    public static void create(String sourceFile,String outputFolder,String outputFile) throws ExecutionException, InterruptedException {
        TranslationConfiguration config =
                TranslationConfiguration.builder()
                        .sourceLocations(new File(sourceFile))
                        .defaultPasses()
                        .defaultLanguages()
                        .build();
        TranslationManager analyzer = TranslationManager.builder().config(config).build();
        TranslationResult translationResult = analyzer.analyze().get();
//        System.out.println(translationResult.getComponents().get(0).getAstChildren());
        System.out.println("test");
        Graph originCPG = getGraph(translationResult);
        CPGjgraphBuilder cpgJgraphBuilder=new CPGjgraphBuilder(originCPG);
        cpgJgraphBuilder.build();
        CPGjgraph cpg =cpgJgraphBuilder.getCpg();
        DotGraphExporter.exportAsDot(cpg.asDefaultDirectedGraph(),outputFolder, outputFile);;

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String sourceFile="D:\\idea_workspace\\SCPG\\src\\test\\resources\\cg.cpp";
        String outputFolder ="D:\\idea_workspace\\SCPG\\src\\test\\resources";
        String outputFile="cg";
        create(sourceFile,outputFolder,outputFile);

    }

//    public void pushToNeo4j(TranslationResult translationResult ) {
//        Benchmark bench = new Benchmark(this.getClass(), "Push cpg to neo4j", false, translationResult);
//        System.out.println("Using import depth: $depth");
//        System.out.println(
//            "Count base nodes to save: " +
//            translationResult.getComponents().size() +
//            translationResult.getAdditionalNodes().size()
//        );
//
//        Pair<Session, SessionFactory> sessionAndSessionFactoryPair = connect();
//
//        val session = sessionAndSessionFactoryPair.first;
//        session.beginTransaction().use { transaction ->
//            session.purgeDatabase();
//            session.save(translationResult.getComponents(), depth);
//            session.save(translationResult.additionalNodes, depth);
//            transaction.commit();
//        }
//
//        session.clear()
//        sessionAndSessionFactoryPair.second.close()
//        bench.addMeasurement()
//    }
}


