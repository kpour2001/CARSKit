package carskit.gaps;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * Created by Mohammad Pourzaferani on 12/12/16.
 */
public class WideRageUsers {
    static String datasetOfRatingPath = "context-aware_data_sets/LDOS-CoMoDa/LDOS-CoMoDa.csv";
    static String evaluationWorkSpacePath = "context-aware_data_sets/LDOS-CoMoDa/CARSKit.Workspace/NMF-rating-predictions fold [4].txt";
    public static void main(String[] args) throws FileNotFoundException {
        Scanner datasetOfRating = new Scanner(new File(datasetOfRatingPath));
        Table<Integer, Integer,Integer> records = HashBasedTable.create();

        //skip first line (Headers)
        datasetOfRating.nextLine();

        while(datasetOfRating.hasNext())
        {
            String[] temp = datasetOfRating.nextLine().split(",");
            records.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        }

        Set users =  records.rowKeySet();
        Map<Integer,Integer> UserEvaluation = new HashMap<>();
        users.forEach(name -> getStatistics(Integer.parseInt(name.toString()),records,UserEvaluation));
        //System.out.println(UserEvaluation);
        System.out.println("--------------------------------------------------------");

        Scanner scanner = new Scanner(new File(evaluationWorkSpacePath));
        Table<Integer, Integer, Double> errorRecords = HashBasedTable.create();
        scanner.nextLine();
        while(scanner.hasNext())
        {
            String[] temp = scanner.nextLine().split("\t");
            errorRecords.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),abs(Double.parseDouble(temp[3])-Double.parseDouble(temp[4])));
        }

        users =  errorRecords.rowKeySet();
        //Map<Integer,Double> UserEvaluationError = new HashMap<>();
        //users.forEach(name -> getErrorStatistics(Integer.parseInt(name.toString()),errorRecords,UserEvaluationError));
        //System.out.println(errorRecords);
        //System.out.println("Find how much Wide Range Users make Error");

        Map<Integer, ArrayList<Integer>> reverseMap = new HashMap<>(
                UserEvaluation.entrySet().stream()
                        .collect(Collectors.groupingBy(Map.Entry::getValue)).values().stream()
                        .collect(Collectors.toMap(
                                item -> item.get(0).getValue(),
                                item -> new ArrayList<>(
                                        item.stream()
                                                .map(Map.Entry::getKey)
                                                .collect(Collectors.toList())
                                ))
                        ));
        reverseMap.forEach((range,usersError) -> calculateMAE(range,errorRecords,usersError));
    }

    public static void calculateMAE(Integer range, Table<Integer, Integer, Double> errorRecords, ArrayList<Integer> Users)
    {
        Double SumMAE=0.0;
        Integer NumRates = 0;
        //System.out.println("Range:"+range);
        for (Integer user:Users
             ) {
            SumMAE += errorRecords.row(user).values().stream().mapToDouble(Number::doubleValue).sum();
            NumRates += errorRecords.row(user).values().size();
            //System.out.println("User:"+user+", Errors:"+errorRecords.row(user));
        }
        System.out.println("range:"+range+", MAE:"+(SumMAE/NumRates)+", Number Of Rates:"+NumRates+", Number of Users:"+Users.size());
    }

//    public static void getErrorStatistics(Integer rowKey, Table errorRecords, Map<Integer,Double> UserEvaluationError)
//    {
//        Map<Integer,Double> attEmployees =  errorRecords.row(rowKey);
//        UserEvaluationError.put(rowKey,attEmployees.values().stream().mapToDouble(Number::doubleValue).sum()/attEmployees.size());
//    }


    public static void getStatistics(Integer rowKey, Table records, Map<Integer,Integer> UserEvaluation)
    {
        Map<Integer,Integer> attEmployees =  records.row(rowKey);

        Integer UserMinRate=5, UserMaxRate=0;
        for(Map.Entry<Integer, Integer> user : attEmployees.entrySet()){
            if (user.getValue()< UserMinRate)
                UserMinRate = user.getValue();
            if (user.getValue()>UserMaxRate)
                UserMaxRate = user.getValue();
        }
        if (attEmployees.size()>=5)
            UserEvaluation.put(rowKey,(UserMaxRate-UserMinRate));
        //System.out.println("    " + rowKey+"   " +(UserMaxRate-UserMinRate));
    }


}
