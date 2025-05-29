package com.aib.scrapperProject.domainLogic.services;


import com.aib.scrapperProject.abstractedHTTP.AbstractionClient;
import com.aib.scrapperProject.configurations.RedisManager;
import com.aib.scrapperProject.domainLogic.model.GlobalCatalog;
import com.aib.scrapperProject.domainLogic.model.pharmaOneModels.PharmaOneModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PharmaOneServices {

    private final AbstractionClient client;
    private final RedisManager redisManager;
    private final ObjectMapper mapper;

    private List<GlobalCatalog> translateFromFarmaciaSanNicolas(List<PharmaOneModel> origin) {
        final List<GlobalCatalog> catalog = new ArrayList<>();
        origin.forEach(sanNicolas -> {
            final String price = sanNicolas.getPrice().replace("$","");
            catalog.add(GlobalCatalog.builder().imageURL(sanNicolas.getImage())
                    .priceProduct(Double.valueOf(price)).nameProduct(sanNicolas.getName()).build());
        }); return catalog;
    }

    public List<GlobalCatalog> SearchByTerm(String termToSearch) {
        final String url = "https://www.farmaciasannicolas.com/productos/keyword/" + termToSearch;// acetaminofen
        final Optional<String> cache = redisManager.getContent(url);
        if (cache.isPresent()) {
            final String root = cache.get();
            try {
                final List<PharmaOneModel> products = mapper.readerFor(new TypeReference<List<PharmaOneModel>>() {
                }).readValue(root);
                return translateFromFarmaciaSanNicolas(products);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        final WebDriver driver = client.setBrowserMimic();
        final List<PharmaOneModel> pharmaOne = new ArrayList<>();
        try {
            driver.get(url);
            final List<WebElement> toJson = driver.findElements(By.xpath("//div[@class='product-item-box']"));
            toJson.forEach(value -> {
                pharmaOne.add(PharmaOneModel.builder().image(value.findElement(By.cssSelector("figure > a > img")).getText()).name(value.findElement(By.cssSelector(".prod-info .prod-name a")).getText()).starterPrice(value.findElement(By.xpath("//div[@class='prices-top']//span[@class='before']")).getText()).price(value.findElement(By.xpath("//div[@class='prices-top']//strong[@class='price']")).getText()).build());
            });
            System.out.println("Content : " + pharmaOne);
            final String content = mapper.writeValueAsString(pharmaOne);
            redisManager.saveContent(url, content, 172800);
        } catch (RuntimeException | JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            driver.close();
        }
        return translateFromFarmaciaSanNicolas(pharmaOne);
    }

}
