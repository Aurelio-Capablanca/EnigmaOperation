package com.aib.scrapperProject.domainLogic.model.pharmaTwoModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@Builder
public class ProductsPharmaTwo {

    private String Nombre;
    private String URLImagen;
    private Double Precio;

}
