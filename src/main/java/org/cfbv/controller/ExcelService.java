package com.example.travelanalyzer.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Service
public class ExcelService {

    public List<Map<String, Object>> analyzeTravelData(String month) {
        List<Map<String, Object>> results = new ArrayList<>();

        try {
            // Load Excel files
            Workbook workbook = new XSSFWorkbook(new FileInputStream(new File("path/to/best_times.xlsx")));
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, String> prices = loadPrices();

            // Get the month index
            int monthIndex = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec").indexOf(month) + 1;

            for (Row row : sheet) {
                if (row.getCell(monthIndex).getStringCellValue().equals("✔")) {
                    Map<String, Object> result = new HashMap<>();
                    String region = row.getCell(0).getStringCellValue();
                    result.put("region", region);
                    result.put("bestTime", month);
                    result.put("price", prices.getOrDefault(region, "N/A"));
                    results.add(result);
                }
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Sort by price (high to low)
        results.sort((a, b) -> {
            String priceA = (String) a.get("price");
            String priceB = (String) b.get("price");
            return (priceA.equals("N/A") ? Integer.MAX_VALUE : Integer.parseInt(priceA)) -
                    (priceB.equals("N/A") ? Integer.MAX_VALUE : Integer.parseInt(priceB));
        });

        return results;
    }

    private Map<String, String> loadPrices() {
        // Load the flight prices from another Excel file
        Map<String, String> prices = new HashMap<>();
        try {
            Workbook workbook = new XSSFWorkbook(new FileInputStream(new File("path/to/flight_prices.xlsx")));
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String region = row.getCell(0).getStringCellValue();
                String price = String.valueOf(row.getCell(1).getNumericCellValue());
                prices.put(region, price);
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prices;
    }
}