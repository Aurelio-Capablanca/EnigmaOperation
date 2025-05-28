package com.aib.scrapperProject.domainLogic.model.walmartModels;

import lombok.Data;

@Data
public class WalmartOffers {

    private String type;
    private Double lowPrice;
    private Double highPrice;
    private String priceCurrency;


}
