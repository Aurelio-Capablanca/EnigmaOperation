package com.aib.scrapperProject.services;


import com.aib.scrapperProject.abstractedHTTP.AbstractionClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.List;

@Service
@AllArgsConstructor
public class PharmaOneServices {

    private final AbstractionClient client;
    private final ObjectMapper mapper;

    private String genericProcessorPharma() {
        final WebDriver driver = client.setBrowserMimic();
        String html = "";
        try {
            final String url = "https://www.farmaciasannicolas.com/productos/keyword/acetaminofen";
            System.out.println("URL : "+url);
            /*
            <div class="products-list-grid">
            <div class="product-item">
            <div class="product-item-box">
            */
            driver.get(url);
            html = driver.getPageSource();
            String content = driver.findElement(By.xpath("//div[@class='products-list-grid']")).getAttribute("innerHTML");
            List<WebElement> toJson = driver.findElements(By.xpath("//div[@class='product-item-box']"));
            System.out.println(toJson.size());
            toJson.forEach(value -> {
                System.out.println("Element : "+value.getAttribute("innerHTML"));
                WebElement image = value.findElement(By.cssSelector("figure > a > img"));
                System.out.println("Image : "+image.getAttribute("src"));
                WebElement name = value.findElement(By.cssSelector(".prod-info .prod-name a"));
                System.out.println("Name : "+name.getText());
                /*
                <div class="prices-top" b-w4r1jh9uvl="">
                <span class="before" b-w4r1jh9uvl="">$0.65</span>
                <strong class="price" b-w4r1jh9uvl="">$0.52</strong>
                </div>
                **/
                WebElement priceStart = value.findElement(By.cssSelector(".prices-top .before"));
                System.out.println("Starter Price : "+priceStart.getText());
                WebElement priceEnd = value.findElement(By.cssSelector(".prices-top .price"));
                System.out.println("End Price : "+priceEnd.getText());
            });
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            driver.close();
        }
        return html;
    }

    public String pharmaOneSearchByTerm(){
        return genericProcessorPharma();
    }

}
