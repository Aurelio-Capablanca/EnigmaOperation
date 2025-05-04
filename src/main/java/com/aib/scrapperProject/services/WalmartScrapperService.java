package com.aib.scrapperProject.services;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WalmartScrapperService {

    private void normalizedURLRequester(String url) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://www.walmart.com/"+url)
                .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/123.0 Safari/537.36")
                .setHeader("Accept-Language", "en-US,en;q=0.9")
                .setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .setFollowRedirect(true)
                .execute()
                .toCompletableFuture()
                .thenAccept(System.out::println)
                .join();

        client.close();
    }

    public void searchProducts(String product, String limit, String page, String sorting){
        try {
            String url = new StringBuilder().append("search?q=").append(product)
                    .append("&sort=").append(sorting)//price_low
                    .append("&page=").append(page)//1
                    .append("&affinityOverride=default")
                    .toString();
            System.out.println(url);
            normalizedURLRequester(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String startWalmartSearch(String product, String limit, String page, String sorting){
        String query = new StringBuilder().append("search?q=").append(product)
                .append("&sort=").append(sorting)//price_low
                .append("&page=").append(page)//1
                .append("&affinityOverride=default")
                .toString();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // or remove for UI
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        WebDriver driver = new ChromeDriver(options);
        String html = "";
        try {
            String url = "https://www.walmart.com/" + query;
            System.out.println("URL: " + url);
            driver.get(url);
            // Wait for page to load
            Thread.sleep(5000); // You can switch to WebDriverWait later
            html = driver.getPageSource();
        } catch (Exception e) {
            html = "Error: " + e.getMessage();
        } finally {
            driver.quit();
        }
        System.out.println(html);
        return html;
    }

}
