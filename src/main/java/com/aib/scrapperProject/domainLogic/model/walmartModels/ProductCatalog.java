package com.aib.scrapperProject.domainLogic.model.walmartModels;

import lombok.Data;

@Data
public class ProductCatalog {

    private String type;
    private Integer position;
    private WalmartItems item;

}
