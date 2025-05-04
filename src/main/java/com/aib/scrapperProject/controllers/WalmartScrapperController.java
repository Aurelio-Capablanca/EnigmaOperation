package com.aib.scrapperProject.controllers;

import com.aib.scrapperProject.services.WalmartScrapperService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WalmartScrapperController {

    private final WalmartScrapperService service;

    @GetMapping("/WalmartSearcher/{product}/{limit}/{page}/{sorting}")
    public ResponseEntity<String> callScrapper(@PathVariable String product, @PathVariable String limit, @PathVariable String page, @PathVariable String sorting) {
        return new ResponseEntity<>(service.startWalmartSearch(product, limit, page, sorting), HttpStatus.OK);
    }

    @GetMapping("/WalmartSearcher/SV")
    public ResponseEntity<String> callPharma(){
        return new ResponseEntity<>(service.pharmaWalmartSV(), HttpStatus.OK);
    }
}
