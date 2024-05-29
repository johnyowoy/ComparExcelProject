package Test.Service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

class Reader {
    private String ExcelFileName;
    private int WorkSheetIndex;
    private int[] Rows;
    Reader(String ExcelFileName, int WorkSheetIndex, int[] Rows) {
        this.ExcelFileName = ExcelFileName;
        this.WorkSheetIndex = WorkSheetIndex;
        this.Rows = Rows;
    }
    public HashSet<ArrayList<String>> ArrayListStore() {
        HashSet<ArrayList<String>> data = new HashSet<>();
        try {
            FileInputStream file = new FileInputStream(new File("Inputdata/" + this.ExcelFileName + ".xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            if (this.WorkSheetIndex >= 0) {
                // 取得Excel工作表
                XSSFSheet sheetWorkbook = workbook.getSheetAt(this.WorkSheetIndex);
                // 取得Excel總行数，共有多少筆資料
                int rowCount = sheetWorkbook.getLastRowNum()+1;
                //System.out.println(rowCount); //顯示工作表共有多少筆資料
                // 循環每一行並且收集指定的資料
                // 從工作表第二行開始(因為第一行是標題)
                for (int i = 1; i < rowCount; i++) {
                    Row row = sheetWorkbook.getRow(i);
                    ArrayList<String> rowData = new ArrayList<>();
                    for (int cellIndex : this.Rows) {
                        Cell cell = row.getCell(cellIndex);
                        String cell2 = getValueFromCell(cell);
                        // 移除特殊字元
                        cell2 = cell2.replaceAll(".tcbadtest.com.tw|.TCBAD.COM.TW|.tcbad.com.tw|.ibcad.com.tw", "");
                        rowData.add(cell2);
                    }
                    // 每行有相同的資料，進行去重複
                    data.add(rowData);
                }
            } else {
                System.out.println("Excel工作表，不可以填寫負數");
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public void showData(HashSet<ArrayList<String>> data) {
        for (ArrayList<String> list : data) {
            System.out.println(list);
        }
    }
    private String getValueFromCell(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}

public class ExcelReader {
    public static void main(String[] args) {

        String excelFileName = "Test1";
        int worksheetIndex = 0;
        int[] rows = {0, 1};  // 假設要讀取的行號
        Reader reader1 = new Reader(excelFileName, worksheetIndex, rows);
        HashSet<ArrayList<String>> result1 = reader1.ArrayListStore();
        reader1.showData(result1);
    }
}
