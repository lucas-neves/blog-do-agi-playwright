package br.com.agibank.tests;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
            .setHeadless(headless);
        
        if (!headless) {
            launchOptions.setArgs(java.util.Arrays.asList("--start-maximized"));
        }
        
        browser = playwright.chromium().launch(launchOptions);
        
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
            .setRecordVideoDir(Paths.get("target/videos"));
        
        if (headless) {
            contextOptions.setViewportSize(1920, 1080);
        } else {
            contextOptions.setViewportSize(null);
        }
        context = browser.newContext(contextOptions);
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            byte[] screenshot = page.screenshot();
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshot));
        }
        
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
