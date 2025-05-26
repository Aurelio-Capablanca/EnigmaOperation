package com.aib.scrapperProject.domainLogic.services;

import com.aib.scrapperProject.abstractedHTTP.AbstractionClient;
import com.aib.scrapperProject.configurations.RedisManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class PharmaTwoServices {

    private final AbstractionClient client;
    private final ObjectMapper mapper;
    private final RedisManager redisManager;

    public String searchByTerm(String argument, int order, int page, int size) {
        final String body = "{\n" +
                "        \"ArticuloNombre\": \"" + argument + "\",\n" +
                "        \"Ordenamiento\": " + order + ",\n" +
                "        \"Pagina\": " + page + ",\n" +
                "        \"TamanoPagina\": " + size + "\n" +
                "    }";
        final String url = "https://www.farmaciaseconomicaselsalvador.com/PROD/ECOMMERCE/API/Articulo/ObtenerArticulosPorNombre";
        String html;
        try {
            html = client.normalizedURLRequester(url, "POST", body);

            //redisManager.saveContent(url, html, 172800);
            //redisManager.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return html;
    }
}
