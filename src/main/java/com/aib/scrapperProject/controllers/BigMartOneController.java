package com.aib.scrapperProject.controllers;

import com.aib.scrapperProject.domainLogic.model.GlobalCatalog;
import com.aib.scrapperProject.domainLogic.model.walmartModels.ProductCatalog;
import com.aib.scrapperProject.domainLogic.services.BigMartOneServices;
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


    @GetMapping("/WalmartSearcher-Pharma/index-pageable/{page}")
    public ResponseEntity<List<GlobalCatalog>> callPharma(@PathVariable String page){
        return new ResponseEntity<>(service.pharmaWalmartInitialPageSV(page), HttpStatus.OK);
    }

    @GetMapping("/WalmartSearcher-Pharma/search-term/{product}")
    public ResponseEntity<List<GlobalCatalog>> searchPharma(@PathVariable String product){
        return new ResponseEntity<>(service.pharmaWalmartSearchProducts(product), HttpStatus.OK);
    }
}
