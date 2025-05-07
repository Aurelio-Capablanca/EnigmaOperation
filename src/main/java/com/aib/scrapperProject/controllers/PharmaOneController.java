package com.aib.scrapperProject.controllers;

import com.aib.scrapperProject.services.PharmaOneServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PharmaOneController {

    private final PharmaOneServices services;

    @GetMapping("/SanNicolas/search-term")
    public String searchByTermPharmaOne(){
        return services.pharmaOneSearchByTerm();
    }

}
