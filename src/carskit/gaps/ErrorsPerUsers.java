package carskit.gaps;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * Created by Mohammad Pourzaferani on 12/12/16.
 */
public class ErrorsPerUsers {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("context-aware_data_sets/LDOS-CoMoDa/CARSKit.Workspace/CAMF_CU-rating-predictions fold [1].txt"));
        scanner.useDelimiter("\\s*");
        Table<Integer, Integer, Double> records = HashBasedTable.create();
        scanner.nextLine();
        while(scanner.hasNext())
        {
            String[] temp = scanner.nextLine().split("\t");
            records.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),abs(Double.parseDouble(temp[3])-Double.parseDouble(temp[4])));
        }

        Set users =  records.rowKeySet();
        users.forEach(name -> getStatistics(Integer.parseInt(name.toString()),records));
    }

    public static void getStatistics(Integer rowKey, Table records)
    {
        Map<Integer,Double> attEmployees =  records.row(rowKey);

        System.out.println(" "+rowKey+" "+attEmployees.values().stream().mapToDouble(Number::doubleValue).sum()/attEmployees.size());
    }
}
