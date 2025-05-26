package com.aib.scrapperProject.domainLogic.model.pharmaTwoModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsPharmaTwo {

    private String Nombre;
    private String URLImagen;
    private Double Precio;

    /*
    "ID": "00023399",
    "Nombre": "Acetaminofen 500mg Ecomed X 10 Tabletas",
    "NombreCorto": "",
    "Codigo_Principal": null,
    "Codigo_Padre": null,
    "Codigo_Hijo": null,
    "URLImagen": "https://www.farmaciaseconomicaselsalvador.com/PROD/SERV_ADMIN_FILES/Archivos/Imagenes/Articulos_PEQ/00023399 (1)_PEQ.jpg",
    "URLGaleria": null,
    "Precio": 0.5100,
    "Categoria": {
        "ID": 8,
        "Nombre": "Analgésicos"
    },
    "SubCategoria": {
        "ID": 0,
        "Nombre": ""
    },
    "Marca": null,
    "Departamento": {
        "ID": 0,
        "Nombre": ""
    },
    "Descripcion": null,
    "PrecioMaximo": 0.600000,
    "EsOferta": true,
    "Mostrar_Etiqueta_Oferta": false,
    "EsPadre": false,
    "AtributoDetalleID": 0,
    "Puntuacion": 0,
    "Calificacion": 0,
    "Limite_Cantidad": 20.000,
    "CABYS": null,
    "IVA": true
    * */

}
