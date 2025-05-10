package com.aib.scrapperProject.abstractedHTTP;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class AbstractionClient {

    public String normalizedURLRequester(String url, String method, String body) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();
        AtomicReference<String> content = new AtomicReference<>();
        client.prepare(method.toUpperCase(),  url)
                .setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/123.0 Safari/537.36")
                .setHeader("Accept-Language", "en-US,en;q=0.9")
                .setHeader("Content-Type", "application/json")
                .setBody(body)
                .setFollowRedirect(true)
                .execute()
                .toCompletableFuture()
                .thenAccept(result -> content.set(result.getResponseBody()))
                .join();
        client.close();
        return content.get();
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
