package utils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil 
{
	public static void writeDataToExcel(String filePath, List<String[]> data) 
	{
		try (Workbook workbook = new XSSFWorkbook()) 
		{
			Sheet sheet = workbook.createSheet("Gym List");
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Gym Name");
			header.createCell(1).setCellValue("Gym Link");
			int rowCount = 1;
			for (String[] rowData : data) 
			{
				Row row = sheet.createRow(rowCount++);
				row.createCell(0).setCellValue(rowData[0]);
				row.createCell(1).setCellValue(rowData[1]);
			}
			try (FileOutputStream out = new FileOutputStream(filePath)) 
			{
				workbook.write(out);
			}
			System.out.println();
			System.out.println("Excel file saved to: " + filePath);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
