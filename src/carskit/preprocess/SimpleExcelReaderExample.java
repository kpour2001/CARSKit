package carskit.preprocess;

/**
 * Created by Mohammad Pourzaferani on 11/17/16.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class SimpleExcelReaderExample {
    public static void main(String[] args)
    {
        try
        {
            FileInputStream file = new FileInputStream(new File("context-aware_data_sets/LDOS-CoMoDa/LDOS-CoMoDa.xls"));

            //Create Workbook instance holding reference to .xlsx file
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                            System.out.print(cell.getCellTypeEnum()==CellType.NUMERIC?cell.getNumericCellValue():cell.getStringCellValue());

                    System.out.print(" - ");
                }
                System.out.println("");
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
