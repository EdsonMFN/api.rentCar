package api.rentCar.File;

import api.rentCar.domains.entity.Rent;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Slf4j
public class FileXlsx {

    public void createFileXlsx(String nameFile, List<Rent> rents){

        log.info("Creating xlsx file");

        try (var workbook = new XSSFWorkbook();
             var outputStream = new FileOutputStream(nameFile)){

             var rentalReport = workbook.createSheet("rental report");

             int lineCounter = 0;

            addHeader1(rentalReport, lineCounter++);
            addHeader(rentalReport, lineCounter++);

            for (Rent rent : rents){
                var row = rentalReport.createRow(lineCounter++);
                addCell(row,0,rent.getId());
                addCell(row,1,rent.getVehicle().getModel().getModel());
                addCell(row,2,rent.getVehicle().getPlate());
                addCell(row,3,rent.getDateWithdrawal());
                addCell(row,4,rent.getDateDelivery());
                addCell(row,5,rent.getRentAmount());
            }
            workbook.write(outputStream);
        } catch (FileNotFoundException ex){
            log.error("file not found {} ",nameFile);
        } catch (IOException e) {
            log.error("error processing the file {} ",nameFile);
        }
        log.info("file " + nameFile + " generated successfully");
    }

    private void addHeader(XSSFSheet rentalReport,int lineCounter) {
        var row = rentalReport.createRow(lineCounter);
        addCell(row,0,"Id");
        addCell(row,1,"Model");
        addCell(row,2,"Plate");
        addCell(row,3,"Date Withdrawal");
        addCell(row,4,"Date Delivery");
        addCell(row,5,"Rent Amount");

    }
    private void addHeader1(XSSFSheet rentalReport,int lineCounter) {
        var row = rentalReport.createRow(lineCounter);
        addCell(row,0,"Report of all car rentals from the rental company");
    }
    private void addCell (Row row,int column, String value){
        Cell cell= row.createCell(column);
        cell.setCellValue(value);
    }
    private void addCell (Row row,int column, int value){
        Cell cell= row.createCell(column);
        cell.setCellValue(value);
    }
    private void addCell (Row row,int column, Long value){
        Cell cell= row.createCell(column);
        cell.setCellValue(value);
    }
    private void addCell (Row row,int column, LocalDate value){
        Cell cell= row.createCell(column);
        cell.setCellValue(value);
    }
    public String base64Exel(final String namefile) {
        try {
            File excelFile = new File(namefile);
            FileInputStream fileInputStream = new FileInputStream(excelFile);
            byte[] excelBytes = new byte[(int) excelFile.length()];
            fileInputStream.read(excelBytes);
            fileInputStream.close();

            byte[] base64Encoded = Base64.getEncoder().encode(excelBytes);

            return new String(base64Encoded);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
