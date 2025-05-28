package com.aib.scrapperProject.controllers;


import com.aib.scrapperProject.domainLogic.model.GlobalCatalog;
import com.aib.scrapperProject.domainLogic.services.PharmaTwoServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PharmaTwoController {

    private final PharmaTwoServices services;


    @GetMapping("/FarmaciasEconomicas/search-by/{nameProduct}/{order}/{page}/{size}")
    public List<GlobalCatalog> pharmaTwoSearchByTerm(@PathVariable String nameProduct, @PathVariable int order, @PathVariable int page, @PathVariable int size){
        return services.searchByTerm(nameProduct, order, page, size);
    }
}
