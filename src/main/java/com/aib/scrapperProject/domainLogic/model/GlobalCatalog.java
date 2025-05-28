package com.aib.scrapperProject.domainLogic.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalCatalog {

    private String nameProduct;
    private String imageURL;
    private Double priceProduct;

}
