import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;

// 蒐集來源資料
class SourceFile {
    private String sourceExcelFile;
    private int workbookSheetIndex;
    SourceFile(String sourceExcelFile, int workbookSheetIndex) {
        this.sourceExcelFile = sourceExcelFile;
        this.workbookSheetIndex = workbookSheetIndex;
    }
    public LinkedHashSet<ArrayList<String>> collectData(int[] cellIndexes) {
        //
        LinkedHashSet<ArrayList<String>> collectedData2 = new LinkedHashSet<>();
        try {
            FileInputStream file = new FileInputStream(new File("Excel\\" + this.sourceExcelFile + ".xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            if (this.workbookSheetIndex >= 0) {
                // 取得Excel工作表
                XSSFSheet sheetWorkbook = workbook.getSheetAt(this.workbookSheetIndex);
                // 取得Excel總行数，共有多少筆資料
                int rowCount = sheetWorkbook.getLastRowNum()+1;
                //System.out.println(rowCount); //顯示工作表共有多少筆資料
                // 循環每一行並且收集指定的資料
                // 從工作表第二行開始(因為第一行是標題)
                for (int i = 1; i < rowCount; i++) {
                    Row row = sheetWorkbook.getRow(i);
                    ArrayList<String> rowData = new ArrayList<>();
                    for (int cellIndex : cellIndexes) {
                        Cell cell = row.getCell(cellIndex);
                        String cell2 = getValueFromCell(cell);
                        // 移除特殊字元
                        cell2 = cell2.replaceAll(".tcbadtest.com.tw|.TCBAD.COM.TW|.tcbad.com.tw|.ibcad.com.tw", "");

                        rowData.add(cell2);
                    }
                    // 相同的行數過濾
                    collectedData2.add(rowData);
                }
            } else {
                System.out.println("Excel工作表，不可以填寫負數");
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return collectedData2;
    }
    public void showCollectData(LinkedHashSet<ArrayList<String>> list) {
        System.out.println(this.sourceExcelFile+"檔案資料，以下內容清單");
        for (ArrayList<String> rowData : list) {
            System.out.println(rowData);
        }
    }
    // 顯示行數
    public void showCollectDataSize(LinkedHashSet<ArrayList<String>> list) {
        System.out.println(this.sourceExcelFile+"檔案，總共有" + list.size()+"筆資料");
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
// 比對資料
class ComparCollectionData {
    private LinkedHashSet<ArrayList<String>> Compararr1;
    private LinkedHashSet<ArrayList<String>> Compararr2;
    ComparCollectionData(LinkedHashSet<ArrayList<String>> CollectionDataArr1, LinkedHashSet<ArrayList<String>> CollectionDataArr2) {
        this.Compararr1 = CollectionDataArr1;
        this.Compararr2 = CollectionDataArr2;
    }
    // 比對IP資訊
    public ArrayList<ArrayList<String>> compareSameIP(int ipIndex) {
        ArrayList<ArrayList<String>> SameIP = new ArrayList<>();
        for (ArrayList<String> Row1 : this.Compararr1) {
            for (ArrayList<String> Row2 : this.Compararr2) {
                if (Row1.get(ipIndex).equals(Row2.get(ipIndex))) {
                    SameIP.add(Row1);
                }
            }
        }
        return SameIP;
    }

    public void showSameIP(ArrayList<ArrayList<String>> list) {
        System.out.println("比對資料相同IP，以下內容清單");
        for (ArrayList<String> rowData : list) {
            System.out.println(rowData);
        }
    }
    public void showSameIPSize(ArrayList<ArrayList<String>> list) {
        System.out.println("比對結果相同IP，總共有" + list.size()+"筆資料");
    }

    public ArrayList<ArrayList<String>> compareDiffIP(int ipIndex) {
        ArrayList<ArrayList<String>> DiffIP = new ArrayList<>();
        for (ArrayList<String> Row1 : this.Compararr1) {
            boolean found = false;
            for (ArrayList<String> Row2 : this.Compararr2) {
                if (Row1.get(ipIndex).equals(Row2.get(ipIndex))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                DiffIP.add(Row1);
            }
        }
        return DiffIP;
    }

    public void showDiffIP(ArrayList<ArrayList<String>> list) {
        System.out.println("比對資料不同IP，以下內容清單");
        for (ArrayList<String> rowData : list) {
            System.out.println(rowData);
        }
    }
    public void showDiffIPSize(ArrayList<ArrayList<String>> list) {
        System.out.println("比對結果不同IP，總共有" + list.size()+"筆資料");
    }
}

class NewWorkbook {
    private Workbook workbook;
    public NewWorkbook() {
        this.workbook = new XSSFWorkbook();
    }
    public void addSheet(String sheetName, ArrayList<ArrayList<String>> data) {
        Sheet sheet = this.workbook.createSheet(sheetName);
        int rowStartNum = 1;
        for (ArrayList<String> rowItem : data) {
            Row row = sheet.createRow(rowStartNum++);
            int cellStartNum = 0;
            for (String cellItem : rowItem) {
                row.createCell(cellStartNum++).setCellValue(cellItem);
            }
        }
    }
    public void writeToFile(String filePath) {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            this.workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
public class Main {
    public static void main(String args[]) {
        var IPMAC = new SourceFile("TEST1", 0);
        LinkedHashSet<ArrayList<String>> IPMAC_CollectionData = IPMAC.collectData(new int[]{0, 1});
        IPMAC.showCollectData(IPMAC_CollectionData);
        IPMAC.showCollectDataSize(IPMAC_CollectionData);
        var WhatsUp = new SourceFile("TEST2", 0);
        LinkedHashSet<ArrayList<String>> WhatsUp_CollectionData = WhatsUp.collectData(new int[]{0, 1});

        var compar1 = new ComparCollectionData(IPMAC_CollectionData, WhatsUp_CollectionData);
        ArrayList<ArrayList<String>> SameIPRows = compar1.compareSameIP(1);
        compar1.showSameIP(SameIPRows);
        compar1.showSameIPSize(SameIPRows);
        ArrayList<ArrayList<String>> DiffIPRows = compar1.compareDiffIP(1);
        compar1.showDiffIP(DiffIPRows);
        compar1.showDiffIPSize(DiffIPRows);

        var newWorkbookSheet = new NewWorkbook();
        newWorkbookSheet.addSheet("sameIP", SameIPRows);
        newWorkbookSheet.addSheet("diffIP", DiffIPRows);
        newWorkbookSheet.writeToFile("Excel\\output.xlsx");

    }
}