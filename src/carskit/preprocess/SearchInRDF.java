package carskit.preprocess;

/**
 * Created by Mohammad Pourzaferani on 11/17/16.
 */

import java.io.IOException;
import org.apache.jena.query.*;
import java.io.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import java.util.*;


import static org.apache.jena.assembler.JA.FileManager;
import static org.apache.jena.assembler.JA.fileManager;

public class SearchInRDF {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        String FileNameEN = "context-aware_data_sets/LinkedMDBTitles.nt";
            Model model = ModelFactory.createDefaultModel();

            model.read(new File("context-aware_data_sets/LinkedMDBTitles.nt").toURL().toString() , "", "N-TRIPLES");


            //String name = (String) it.next();
            String name = "<http://purl.org/dc/terms/title>";
            String query = "SELECT distinct ?a ?b  WHERE { ?a " + name + " ?b.}";//
            Query internal_query = QueryFactory.create(query);
            QueryExecution qexec = QueryExecutionFactory.create(internal_query, model);
            ResultSet results = qexec.execSelect();
            try {
                while (results.hasNext()) {
                    RDFNode a, b;
                    QuerySolution soln = results.nextSolution();
                    a = soln.get("?a");
                    b = soln.get("?b");
//                    FileWriter writer1 = new FileWriter(s.GetRoot() + "case.Superiors.nt", true);
//                    writer1.write("<" + a.toString() + "> <" + b.toString() + "> " + name + " .\n");
//                    writer1.close();
                    System.out.println("<" + a.toString() + ">" + name +" <" + b.toString() + ">");
                }
            } finally {
            }
    }
}
