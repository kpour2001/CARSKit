package carskit.alg.cars.adaptation.dependent.SemanticMF;

import java.io.FileNotFoundException;
import java.lang.Exception;
import java.io.*;

import carskit.alg.cars.adaptation.dependent.CAMF;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import happy.coding.io.FileConfiger;
import happy.coding.io.*;
import happy.coding.io.Logs;
import org.apache.jena.query.* ;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.util.*;

import static java.io.File.separator;

/**
 * Created by Mohammad Pourzaferani on 12/21/16.
 */
public class SpreadingActivation{
    final static Logger logger = Logger.getLogger(SpreadingActivation.class);
    protected static String defaultConfigFileName = "setting.conf";



    public SpreadingActivation() throws Exception {
    }

    public static void main(String[] args) throws Exception {

        //Read and build RDF Model
        System.out.println(getChildsOfNode("<http://data.linkedmdb.org/resource/film/1>"));

        //Read the file map context-aware dataset to LinkedMDB
         FileConfiger cf = new FileConfiger(defaultConfigFileName);
         String datasetMappingFile=cf.getPath("dataset.mapping");

        if(!FileIO.exist(datasetMappingFile))
            Logs.error("Your mapping file path is incorrect: File doesn't exist. Please double check your configuration.");
        else {
            Map<Integer, String> mappedMovies = readMappingFile(datasetMappingFile);
        }

        String test = "";

        }


    //get a URI and return it's childs
    public static Multimap<String,String> getChildsOfNode(String URIofSubject)
    {
        Model model = ModelFactory.createDefaultModel();
        Multimap<String,String> childs = ArrayListMultimap.create();
        InputStream LinkedMDB = FileManager.get().open("context-aware_data_sets/LinkedMDBTitles.nt");
        model.read(LinkedMDB, "", "N-TRIPLES");
        String query = "SELECT ?predicate ?object  WHERE {"+URIofSubject+" ?predicate ?object.}";
        Query internal_query = QueryFactory.create(query);
        QueryExecution qexec = QueryExecutionFactory.create(internal_query, model);
        ResultSet results = qexec.execSelect();
        try {
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                childs.put(soln.get("?predicate").toString(),soln.get("?object").toString());
            }
            return childs;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<Integer, String> readMappingFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        Map<Integer, String> records = new HashMap<>();
        scanner.nextLine();
        while (scanner.hasNext()) {
            String[] temp = scanner.nextLine().split(",");
            logger.info(temp[1]);
            if(temp.length > 4)
            records.put(Integer.parseInt(temp[0]),temp[3]);
        }
        return records;
    }
}
