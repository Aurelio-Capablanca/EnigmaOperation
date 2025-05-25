package com.aib.scrapperProject.domainLogic.services;


import com.aib.scrapperProject.abstractedHTTP.AbstractionClient;
import com.aib.scrapperProject.domainLogic.model.pharmaOneModels.PharmaOneModel;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PharmaOneServices {

    private final AbstractionClient client;

    public List<PharmaOneModel> SearchByTerm(String termToSearch) {
        final WebDriver driver = client.setBrowserMimic();
        final List<PharmaOneModel> pharmaOne = new ArrayList<>();
        try {
            final String url = "https://www.farmaciasannicolas.com/productos/keyword/"+termToSearch;// acetaminofen
            System.out.println("URL : " + url);
            driver.get(url);
            final List<WebElement> toJson = driver.findElements(By.xpath("//div[@class='product-item-box']"));
            toJson.forEach(value -> {
                pharmaOne.add(PharmaOneModel.builder()
                        .image(value.findElement(By.cssSelector("figure > a > img")).getText())
                        .name(value.findElement(By.cssSelector(".prod-info .prod-name a")).getText())
                                .starterPrice(value.findElement(By.xpath("//div[@class='prices-top']//span[@class='before']")).getText())
                                .price(value.findElement(By.xpath("//div[@class='prices-top']//strong[@class='price']")).getText())
                        .build());
            });
            pharmaOne.forEach(System.out::println);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            driver.close();
        }
        return pharmaOne;
    }

}
