package com.aib.scrapperProject.abstractedHTTP;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AbstractionClient {

    public void normalizedURLRequester(String url) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        client.prepare("GET", "https://www.walmart.com/" + url)
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

    public WebDriver setBrowserMimic() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // or remove for UI
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        Map<String, Object> prefs = Map.of("profile.default_content_setting_values.images", 2,
                "profile.managed_default_content_settings.stylesheets", 2,
                "profile.managed_default_content_settings.plugins", 2,
                "profile.managed_default_content_settings.popups", 2,
                "profile.managed_default_content_settings.geolocation", 2,
                "profile.managed_default_content_settings.notifications", 2,
                "profile.managed_default_content_settings.auto_select_certificate", 2);
        options.setExperimentalOption("prefs", prefs);
        return new ChromeDriver(options);
    }

}
