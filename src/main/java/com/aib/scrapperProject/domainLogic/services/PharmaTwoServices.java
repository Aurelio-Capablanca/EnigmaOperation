package com.aib.scrapperProject.domainLogic.services;

import com.aib.scrapperProject.abstractedHTTP.AbstractionClient;
import com.aib.scrapperProject.configurations.RedisManager;
import com.aib.scrapperProject.domainLogic.model.GlobalCatalog;
import com.aib.scrapperProject.domainLogic.model.pharmaOneModels.PharmaOneModel;
import com.aib.scrapperProject.domainLogic.model.pharmaTwoModels.ProductsPharmaTwo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PharmaTwoServices {

    private final AbstractionClient client;
    private final ObjectMapper mapper;
    private final RedisManager redisManager;

    private List<GlobalCatalog> translateFromFarmaciaEconomicas(List<ProductsPharmaTwo> origin) {
        final List<GlobalCatalog> catalog = new ArrayList<>();
        origin.forEach(eco -> catalog.add(GlobalCatalog.builder()
                .imageURL(eco.getURLImagen())
                .priceProduct(eco.getPrecio())
                .nameProduct(eco.getNombre()).build()));
        return catalog;
    }


    public List<GlobalCatalog> searchByTerm(String argument, int order, int page, int size) {
        final String url = "https://www.farmaciaseconomicaselsalvador.com/PROD/ECOMMERCE/API/Articulo/ObtenerArticulosPorNombre";
        final Optional<String> content = redisManager.getContent(url);
        if (content.isPresent()) {
            final String root = content.get();
            try {
                final List<ProductsPharmaTwo> products = mapper.readerFor(new TypeReference<List<ProductsPharmaTwo>>() {
                }).readValue(root);
                return translateFromFarmaciaEconomicas(products);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        final String html;
        final String body = "{\n" +
                "        \"ArticuloNombre\": \"" + argument + "\",\n" +
                "        \"Ordenamiento\": " + order + ",\n" +
                "        \"Pagina\": " + page + ",\n" +
                "        \"TamanoPagina\": " + size + "\n" +
                "    }";
        final List<ProductsPharmaTwo> catalog;
        try {
            html = client.normalizedURLRequester(url, "POST", body);
            JsonNode node = mapper.readTree(html).path("Data").path("PaginaItems");
            System.out.println("Node : "+node.toString());
            catalog = mapper.readerFor(new TypeReference<List<ProductsPharmaTwo>>() {
            }).readValue(node);
            System.out.println("Parsed : "+catalog);
            redisManager.saveContent(url, node.toString(), 172800);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return translateFromFarmaciaEconomicas(catalog);
    }
}
