package carskit.gaps;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * Created by Mohammad Pourzaferani on 12/19/16.
 */
public class MakeDatasetClear {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("context-aware_data_sets/LDOS-CoMoDa/LDOS-CoMoDa.csv"));
        Table<Integer, Integer, Integer> records = HashBasedTable.create();
        scanner.nextLine();
        while(scanner.hasNext())
        {
            String[] temp = scanner.nextLine().split(",");
            records.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        }
        scanner = new Scanner(new File("context-aware_data_sets/LDOS-CoMoDa/itemsTitles.csv"));
        Map record = new HashMap();
        scanner.nextLine();
        while(scanner.hasNext())
        {
            String[] temp = scanner.nextLine().split(",");
            record.put(Integer.parseInt(temp[0]), temp[1]);
        }

        scanner = new Scanner(new File("context-aware_data_sets/LDOS-CoMoDa/Evaluation.csv"));
        Map recordEvaluation = new HashMap();
        scanner.nextLine();
        while(scanner.hasNext())
        {
            String[] temp = scanner.nextLine().split(",");
            recordEvaluation.put(temp[0]+","+temp[1],temp[13]);
        }

        for (Integer FilmID:records.rowKeySet()) {
            records.row(FilmID).forEach((k,v)-> System.out.println(FilmID+","+record.get(k)+","+v+","+recordEvaluation.get(FilmID+","+k)));

        }

    }
}
