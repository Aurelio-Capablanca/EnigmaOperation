package com.aib.scrapperProject.domainLogic.services;

import com.aib.scrapperProject.abstractedHTTP.AbstractionClient;
import com.aib.scrapperProject.configurations.RedisManager;
import com.aib.scrapperProject.domainLogic.model.GlobalCatalog;
import com.aib.scrapperProject.domainLogic.model.walmartModels.ProductCatalog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class BigMartOneServices {

    private final AbstractionClient client;
    private final ObjectMapper mapper;
    private final RedisManager redisManager;

    private List<GlobalCatalog> traduceCatalogFromWalmart(List<ProductCatalog> origin) {
        final List<GlobalCatalog> catalog = new ArrayList<>();
        origin.forEach(walmart ->
            catalog.add(GlobalCatalog.builder()
                    .imageURL(walmart.getItem().getImage())
                    .nameProduct(walmart.getItem().getName())
                    .priceProduct(walmart.getItem().getOffers().getHighPrice())
                    .build()));
        return catalog;
    }

    private List<GlobalCatalog> genericProcessorWalmart(String url) {
        System.out.println("URL: " + url);
        final Optional<String> content = redisManager.getContent(url);
        if (content.isPresent()) {
            final String root = content.get();
            System.out.println("Content Got: " + root);
            try {
                JsonNode node = mapper.readTree(root).path("itemListElement");
                final List<ProductCatalog> products =  mapper.readerFor(new TypeReference<List<ProductCatalog>>() {
                }).readValue(node);
                return traduceCatalogFromWalmart(products);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        final WebDriver driver = client.setBrowserMimic();
        final List<ProductCatalog> catalog;
        try {
            driver.get(url);
            String jsonLD = driver.findElement(By.xpath("//div[@class='flex flex-column min-vh-100 w-100']//script[@type='application/ld+json']")).getAttribute("innerHTML");
            if (jsonLD == null) return Collections.emptyList();
            jsonLD = jsonLD.replaceAll("@", "");
            System.out.println("Walmart JSON : " + jsonLD);
            JsonNode root = mapper.readTree(jsonLD).path("itemListElement");
            catalog = mapper.readerFor(new TypeReference<List<ProductCatalog>>() {
            }).readValue(root);
            redisManager.saveContent(url, jsonLD, 172800);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
        return traduceCatalogFromWalmart(catalog);
    }

    public List<GlobalCatalog> pharmaWalmartInitialPageSV(String page) {
        final String url = "https://www.walmart.com.sv/farmacia?page=" + page;
        return genericProcessorWalmart(url);
    }


    public List<GlobalCatalog> pharmaWalmartSearchProducts(String productSearch) {
        final String url = new StringBuilder().append("https://www.walmart.com.sv/")
                .append(productSearch).append("?_q=").append(productSearch).append("&map=ft").toString();
        return genericProcessorWalmart(url);
    }

}
