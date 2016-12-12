package carskit.gaps;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Mohammad Pourzaferani on 12/12/16.
 */
public class WideRageUsers {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("context-aware_data_sets/LDOS-CoMoDa/LDOS-CoMoDa.csv"));
        scanner.useDelimiter(";");
        Table<Integer, Integer,Integer> records = HashBasedTable.create();
        scanner.nextLine();
        while(scanner.hasNext())
        {

            String[] temp = scanner.nextLine().split(",");
            records.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        }
        Set users =  records.rowKeySet();
        users.forEach(name -> getStatistics(Integer.parseInt(name.toString()),records));

    }

    public static void getStatistics(Integer rowKey, Table records)
    {
        Map<Integer,Integer> attEmployees =  records.row(rowKey);

        Integer UserMinRate=5, UserMaxRate=0;
        for(Map.Entry<Integer, Integer> user : attEmployees.entrySet()){
            if (user.getValue()< UserMinRate)
                UserMinRate = user.getValue();
            if (user.getValue()>UserMaxRate)
                UserMaxRate = user.getValue();
        }
        System.out.println("    " + rowKey+"   " + UserMinRate + "  " + UserMaxRate+"   "+attEmployees.size());
    }


}
