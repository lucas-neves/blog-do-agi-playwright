package br.com.agibank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductSearchPage {
    private final Page page;
    private final Locator produtosMenu;
    private final Locator subMenu;
    private final Locator searchButton;
    private final Locator searchField;
    private final Locator pageTitle;

    public ProductSearchPage(Page page) {
        this.page = page;
        this.produtosMenu = page.locator("#ast-hf-menu-1 li:has-text('Produtos')").first();
        this.subMenu = produtosMenu.locator("ul.sub-menu").first();
        this.searchButton = page.locator("a.slide-search");
        this.searchField = page.locator("#search-field");
        this.pageTitle = page.locator("h1.page-title");
    }

    public void hoverProdutosMenu() {
        produtosMenu.hover();
        subMenu.waitFor();
    }

    public String getRandomProductFromSubMenu() {
        Locator items = subMenu.locator("li");
        int count = items.count();
        int randomIndex = (int) (Math.random() * count);
        return items.nth(randomIndex).innerText().trim();
    }

    public void clickSearchButton() {
        searchButton.waitFor();
        page.waitForTimeout(2000);
        searchButton.click();
        searchField.waitFor();
    }

    public void searchForProduct(String productName) {
        searchField.fill(productName);
        searchField.press("Enter");
    }

    public String getCurrentUrl() {
        return page.url();
    }

    public String getPageTitleText() {
        return pageTitle.innerText();
    }
}
