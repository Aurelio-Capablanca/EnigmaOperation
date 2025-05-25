package com.aib.scrapperProject.domainLogic.model.pharmaTwoModels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PharmaTwoRequest {

    private String name;
    private Integer sorting;
    private Integer page;
    private Integer sizePage;

}
