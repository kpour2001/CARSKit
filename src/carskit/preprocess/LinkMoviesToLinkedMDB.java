package carskit.preprocess;

/**
 * Created by Mohammad Pourzaferani on 11/16/16.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LinkMoviesToLinkedMDB {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("context-aware_data_sets/LDOS-CoMoDa/itemsTitles.csv"));
        scanner.useDelimiter(";");
        while(scanner.hasNext()){
            System.out.print(scanner.next()+"|");
        }
        scanner.close();
    }
}
