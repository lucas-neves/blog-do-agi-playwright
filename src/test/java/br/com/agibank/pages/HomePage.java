package br.com.agibank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {
    private final Page page;
    private final Locator verMaisStoriesButton;

    public HomePage(Page page) {
        this.page = page;
        this.verMaisStoriesButton = page.locator(".uagb-marketing-btn__link");
    }

    public void navigate() {
        page.navigate("https://blog.agibank.com.br/");
    }

    public void clickVerMaisStories() {
        verMaisStoriesButton.click();
        page.waitForURL("**/web-stories/**");
    }

    public String getCurrentUrl() {
        return page.url();
    }
}
