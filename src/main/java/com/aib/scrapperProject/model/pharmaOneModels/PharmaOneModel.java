package com.aib.scrapperProject.model.pharmaOneModels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PharmaOneModel {

    private String image;
    private String name;
    private String starterPrice;
    private String price;

}
