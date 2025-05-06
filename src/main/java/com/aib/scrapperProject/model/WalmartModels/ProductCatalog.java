package com.aib.scrapperProject.model.WalmartModels;

import lombok.Data;

@Data
public class ProductCatalog {

    private String type;
    private Integer position;
    private WalmartItems item;

}
