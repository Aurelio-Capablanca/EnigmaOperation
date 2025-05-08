package com.aib.scrapperProject.model.pharmaTwoModels;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PharmaTwoRequest {

    /*
    {
        "ArticuloNombre": "acetaminofen",
        "Ordenamiento": 0,
        "Pagina": 1,
        "TamanoPagina": 12
    }
    **/

    private String name;
    private Integer sorting;
    private Integer page;
    private Integer sizePage;

}
