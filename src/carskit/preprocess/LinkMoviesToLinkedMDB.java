package carskit.preprocess;

/**
 * Created by Mohammad Pourzaferani on 11/16/16.
 */

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class LinkMoviesToLinkedMDB{
    public static void main(String[] args) throws IOException {
        File file = new File("LinkedMovies.csv");
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

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
                movieList.put(subject.toString(),object.toString().toLowerCase());
            }
        } finally {
        }

        Scanner scanner = new Scanner(new File("context-aware_data_sets/LDOS-CoMoDa/itemsTitles.csv"));
        scanner.useDelimiter(";");
        Map titles = new HashMap();
        while(scanner.hasNext()){
            String[] temp = scanner.nextLine().split(",");
            titles.put(temp[0],temp[1]);
            Set FinalMatchingList = getKeysByValue(movieList,temp[1].toLowerCase().replaceAll(" \\([0-9][0-9][0-9][0-9]\\)",""));
            String movieName=temp[1].toLowerCase().replaceAll(" \\([0-9][0-9][0-9][0-9]\\)","");
            if(FinalMatchingList.isEmpty())
            {
                Iterator it = movieList.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    if (pair.getValue().toString().contains(temp[1].toLowerCase())) {
                        FinalMatchingList.add(pair.getKey());
                        movieName = pair.getValue().toString();
                    }
                    if (pair.getValue().toString().toLowerCase().equals(temp[1].toLowerCase().replaceAll("and", "&"))) {
                        FinalMatchingList.add(pair.getKey());
                        movieName = pair.getValue().toString();
                    }
                    if (pair.getValue().toString().equals(temp[1].replaceAll("1", "I").toLowerCase())) {
                        FinalMatchingList.add(pair.getKey());
                        movieName = pair.getValue().toString();
                    }
                    if (pair.getValue().toString().equals(temp[1].replaceAll("2", "II").toLowerCase())) {
                        FinalMatchingList.add(pair.getKey());
                        movieName = pair.getValue().toString();
                    }
                    if (pair.getValue().toString().equals(temp[1].replaceAll("3", "III").toLowerCase())) {
                        FinalMatchingList.add(pair.getKey());
                        movieName = pair.getValue().toString();
                    }
                    if (pair.getValue().toString().equals(temp[1].replaceAll("4", "IV").toLowerCase())) {
                        FinalMatchingList.add(pair.getKey());
                        movieName = pair.getValue().toString();
                    }
                    if (pair.getValue().toString().equals(temp[1].replaceAll("5", "V").toLowerCase())) {
                        FinalMatchingList.add(pair.getKey());
                        movieName = pair.getValue().toString();
                    }
                    //it.remove(); // avoids a ConcurrentModificationException
                }
            }

            if (FinalMatchingList.isEmpty())
                bw.write(temp[0]+","+temp[1]+",");
            else {
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
                bw.write(temp[0] + "," + temp[1] + "," + movieName);

                FinalMatchingList.forEach(content -> {
                    try {
                        bw.write("," + content.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            bw.write("\n");



            // if file doesnt exists, then create it

        }
        bw.close();
        scanner.close();
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

}
