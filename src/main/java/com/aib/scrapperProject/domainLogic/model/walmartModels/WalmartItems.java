package com.aib.scrapperProject.domainLogic.model.walmartModels;

import lombok.Data;

@Data
public class WalmartItems {

    private String context;
    private String type;
    private String id;
    private String name;
    private WalmartBrands brand;
    private String image;
    private String description;
    private String mpn;
    private String sku;
    private WalmartOffers offers;

}
