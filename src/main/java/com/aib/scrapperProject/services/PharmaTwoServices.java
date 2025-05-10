package com.aib.scrapperProject.services;

import com.aib.scrapperProject.abstractedHTTP.AbstractionClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class PharmaTwoServices {

    private final AbstractionClient client;

    public String searchByTerm() {
        //WebDriver driver = client.setBrowserMimic();
        final String body = "{\n" +
                "        \"ArticuloNombre\": \"acetaminofen\",\n" +
                "        \"Ordenamiento\": 0,\n" +
                "        \"Pagina\": 1,\n" +
                "        \"TamanoPagina\": 12\n" +
                "    }";
        final String url = "https://www.farmaciaseconomicaselsalvador.com/PROD/ECOMMERCE/API/Articulo/ObtenerArticulosPorNombre";
        String html;
        try {
            html = client.normalizedURLRequester(url, "POST", body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return html;
    }
}
