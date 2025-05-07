package com.aib.scrapperProject.controllers;

import com.aib.scrapperProject.model.WalmartModels.ProductCatalog;
import com.aib.scrapperProject.services.BigMartOneServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BigMartOneController {

    private final BigMartOneServices service;

    @GetMapping("/WalmartSearcher/{product}/{limit}/{page}/{sorting}")
    public ResponseEntity<String> callScrapper(@PathVariable String product, @PathVariable String limit, @PathVariable String page, @PathVariable String sorting) {
        return new ResponseEntity<>(service.startWalmartSearch(product, limit, page, sorting), HttpStatus.OK);
    }

    @GetMapping("/WalmartSearcher-Pharma/index-pageable/{page}")
    public ResponseEntity<List<ProductCatalog>> callPharma(@PathVariable String page){
        return new ResponseEntity<>(service.pharmaWalmartInitialPageSV(page), HttpStatus.OK);
    }

    @GetMapping("/WalmartSearcher-Pharma/search-term/{product}")
    public ResponseEntity<List<ProductCatalog>> searchPharma(@PathVariable String product){
        return new ResponseEntity<>(service.pharmaWalmartSearchProducts(product), HttpStatus.OK);
    }
}
