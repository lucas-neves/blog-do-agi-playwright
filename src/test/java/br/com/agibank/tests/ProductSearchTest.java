package br.com.agibank.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

import br.com.agibank.pages.HomePage;
import br.com.agibank.pages.ProductSearchPage;

public class ProductSearchTest extends BaseTest {

    @Test
    @Description("Valida busca de produtos através do menu e campo de pesquisa")
    public void testProductSearch() {
        HomePage homePage = new HomePage(page);
        ProductSearchPage productSearchPage = new ProductSearchPage(page);

        navigateToHomePage(homePage);
        hoverProdutosMenu(productSearchPage);
        String productName = getRandomProduct(productSearchPage);
        clickSearchButton(productSearchPage);
        searchProduct(productSearchPage, productName);
        validateSearchUrl(productSearchPage, productName);
        validatePageTitle(productSearchPage, productName);
    }

    @Step("Navegar para página inicial do blog")
    private void navigateToHomePage(HomePage homePage) {
        homePage.navigate();
    }

    @Step("Passar mouse no menu Produtos")
    private void hoverProdutosMenu(ProductSearchPage productSearchPage) {
        productSearchPage.hoverProdutosMenu();
    }

    @Step("Obter produto aleatório do submenu")
    private String getRandomProduct(ProductSearchPage productSearchPage) {
        String productName = productSearchPage.getRandomProductFromSubMenu();
        System.out.println("Produto selecionado: " + productName);
        return productName;
    }

    @Step("Clicar no botão de busca (lupa)")
    private void clickSearchButton(ProductSearchPage productSearchPage) {
        productSearchPage.clickSearchButton();
    }

    @Step("Buscar por produto: {productName}")
    private void searchProduct(ProductSearchPage productSearchPage, String productName) {
        productSearchPage.searchForProduct(productName);
    }

    @Step("Validar URL contém termo de busca")
    private void validateSearchUrl(ProductSearchPage productSearchPage, String productName) {
        String currentUrl = productSearchPage.getCurrentUrl();
        try {
            String decodedUrl = java.net.URLDecoder.decode(currentUrl, "UTF-8");
            Assert.assertTrue(decodedUrl.contains("?s=") && decodedUrl.contains(productName), 
                "URL não contém ?s=" + productName + " - URL atual: " + decodedUrl);
        } catch (Exception e) {
            Assert.assertTrue(currentUrl.contains("?s="), 
                "URL não contém parâmetro de busca - URL atual: " + currentUrl);
        }
    }

    @Step("Validar título da página contém termo buscado")
    private void validatePageTitle(ProductSearchPage productSearchPage, String productName) {
        String titleText = productSearchPage.getPageTitleText();
        Assert.assertTrue(titleText.toLowerCase().contains(productName.toLowerCase()), 
            "Título da página não contém '" + productName + "' - Título atual: " + titleText);
    }
}
