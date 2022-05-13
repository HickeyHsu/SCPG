package scpg;
import de.fraunhofer.aisec.cpg.TranslationConfiguration;
import de.fraunhofer.aisec.cpg.TranslationManager;
import de.fraunhofer.aisec.cpg.TranslationResult;
import de.fraunhofer.aisec.cpg.helpers.Benchmark;
import static de.fraunhofer.aisec.cpg.graph.GraphKt.getGraph;
import de.fraunhofer.aisec.cpg.helpers.BenchmarkResults;




import de.fraunhofer.aisec.cpg.graph.Graph;
import scpg.builder.CPGjgraphBuilder;
import scpg.graph.CPGjgraph;
import scpg.utils.DotGraphExporter;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class Generate{
    public static void create() throws ExecutionException, InterruptedException {
        TranslationConfiguration config =
                TranslationConfiguration.builder()
                        .sourceLocations(new File("D:\\idea_workspace\\SCPG\\src\\test\\resources\\cg.cpp"))
                        .defaultPasses()
                        .defaultLanguages()
                        .build();
        TranslationManager analyzer = TranslationManager.builder().config(config).build();
        TranslationResult translationResult = analyzer.analyze().get();
        System.out.println("test");
//        translationResult.getBenchmarkResults().print();//打印时间、进程等
        Graph originCPG = getGraph(translationResult);
        CPGjgraphBuilder cpgJgraphBuilder=new CPGjgraphBuilder(originCPG);
        cpgJgraphBuilder.build();
        CPGjgraph cpg =cpgJgraphBuilder.getCpg();
        DotGraphExporter.exportAsDot(cpg.asDefaultDirectedGraph(),"D:\\idea_workspace\\SCPG\\src\\test\\resources", "cg");;

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        create();

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


