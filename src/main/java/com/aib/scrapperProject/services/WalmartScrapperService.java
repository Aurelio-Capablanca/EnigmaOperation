package com.aib.scrapperProject.services;

import com.aib.scrapperProject.abstractedHTTP.AbstractionClient;
import com.aib.scrapperProject.model.WalmartModels.ProductCatalog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class WalmartScrapperService {

    private final AbstractionClient client;
    private final ObjectMapper mapper;

    public String startWalmartSearch(String product, String limit, String page, String sorting) {
        final WebDriver driver = client.setBrowserMimic();
        String query = new StringBuilder().append("search?q=").append(product)
                .append("&sort=").append(sorting)//price_low
                .append("&page=").append(page)//1
                .append("&affinityOverride=default")
                .toString();

        String html = "";
        try {
            String url = "https://www.walmart.com/" + query;
            System.out.println("URL: " + url);
            driver.get(url);
            Thread.sleep(5000);
            html = driver.getPageSource();
        } catch (Exception e) {
            html = "Error: " + e.getMessage();
        } finally {
            driver.quit();
        }
        System.out.println(html);
        return html;
    }

    public List<ProductCatalog> pharmaWalmartInitialPageSV(String page) {
        final WebDriver driver = client.setBrowserMimic();
        List<ProductCatalog> catalog = new ArrayList<>();
        try {
            String url = "https://www.walmart.com.sv/farmacia?page=" + page;
            System.out.println("URL: " + url);
            driver.get(url);
           // Thread.sleep(5000);
            String jsonLD = driver.findElement(By.xpath("//div[@class='flex flex-column min-vh-100 w-100']//script[@type='application/ld+json']")).getAttribute("innerHTML");
            if (jsonLD == null) return Collections.emptyList();
            jsonLD = jsonLD.replaceAll("@", "");
            System.out.println(jsonLD);
            JsonNode root = mapper.readTree(jsonLD).path("itemListElement");
            catalog = mapper.readerFor(new TypeReference<List<ProductCatalog>>() {
            }).readValue(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
        return catalog;
    }


    public String pharmaWalmartSearchProducts(String productSearch) {
        final WebDriver driver = client.setBrowserMimic();
        String middleMan = "";
        try {
            final StringBuilder concats = new StringBuilder().append("https://www.walmart.com.sv/")
                    .append(productSearch).append("?_q=").append(productSearch).append("&map=ft");
            driver.get(concats.toString());
            middleMan = driver.getPageSource();

            String jsonLD = driver.findElement(By.xpath("//div[@class='flex flex-column min-vh-100 w-100']//script[@type='application/ld+json']")).getAttribute("innerHTML");
            if (jsonLD == null) return "";
            jsonLD = jsonLD.replaceAll("@", "");
            System.out.println(jsonLD);
            JsonNode root = mapper.readTree(jsonLD).path("itemListElement");

            List<ProductCatalog> catalog = mapper.readerFor(new TypeReference<List<ProductCatalog>>() {
            }).readValue(root);
            System.out.println("Content : "+catalog);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
        return middleMan;
    }


}
