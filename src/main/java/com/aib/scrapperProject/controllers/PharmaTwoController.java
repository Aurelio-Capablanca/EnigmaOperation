package com.aib.scrapperProject.controllers;


import com.aib.scrapperProject.services.PharmaTwoServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PharmaTwoController {

    private final PharmaTwoServices services;


    @GetMapping("/FarmaciasEconomicas/search-by")
    public String pharmaTwoSearchByTerm(){
        return services.searchByTerm();
    }
}
