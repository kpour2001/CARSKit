package carskit.preprocess;

/**
 * Created by Mohammad Pourzaferani on 11/16/16.
 */

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LinkMoviesToLinkedMDB{
    public static void main(String[] args) throws IOException {
        String FileNameEN = "context-aware_data_sets/LinkedMDBTitles.nt";
        Model model = ModelFactory.createDefaultModel();
        model.read(new File("context-aware_data_sets/LinkedMDBTitles.nt").toURL().toString() , "", "N-TRIPLES");

        //String name = (String) it.next();
        String name = "<http://purl.org/dc/terms/title>";
        String query = "SELECT distinct ?subject ?object  WHERE { ?subject " + name + " ?object.}";//
        Query internal_query = QueryFactory.create(query);
        QueryExecution qexec = QueryExecutionFactory.create(internal_query, model);
        ResultSet results = qexec.execSelect();
        Map movieList = new HashMap();
        try {
            while (results.hasNext()) {
                RDFNode subject, object;
                QuerySolution soln = results.nextSolution();
                subject = soln.get("?subject");
                object = soln.get("?object");
                movieList.put(subject.toString(),object.toString());
            }
        } finally {
        }

        Scanner scanner = new Scanner(new File("context-aware_data_sets/LDOS-CoMoDa/itemsTitles.csv"));
        scanner.useDelimiter(";");
        Map titles = new HashMap();
        while(scanner.hasNext()){
            String[] temp = scanner.nextLine().split(";");
            titles.put(temp[0],temp[1]);
        }
        scanner.close();
    }
}
