package com.example.travelanalyzer.controller;

import com.example.travelanalyzer.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/travel")
public class TravelController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/analyze")
    public List<Map<String, Object>> analyze(@RequestParam String month) {
        return excelService.analyzeTravelData(month);
    }
}
