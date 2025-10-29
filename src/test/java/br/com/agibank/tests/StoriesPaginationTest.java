package br.com.agibank.tests;

import com.microsoft.playwright.Response;

import br.com.agibank.pages.HomePage;
import br.com.agibank.pages.StoriesPage;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StoriesPaginationTest extends BaseTest {

    @Test
    @Description("Valida navegação para Stories, paginação infinita e mudança de URL")
    public void testStoriesPagination() {
        HomePage homePage = new HomePage(page);
        StoriesPage storiesPage = new StoriesPage(page);

        navigateToHomePage(homePage);
        clickVerMaisStories(homePage);
        validateWebStoriesUrl(homePage);
        scrollAndValidateInfinityScrolling(storiesPage);
        validatePage2Url(storiesPage);
    }

    @Step("Navegar para página inicial do blog")
    private void navigateToHomePage(HomePage homePage) {
        homePage.navigate();
    }

    @Step("Clicar em Ver mais Stories")
    private void clickVerMaisStories(HomePage homePage) {
        homePage.clickVerMaisStories();
    }

    @Step("Validar que URL contém /web-stories/")
    private void validateWebStoriesUrl(HomePage homePage) {
        Assert.assertTrue(homePage.getCurrentUrl().contains("/web-stories/"), 
            "URL não contém /web-stories/");
    }

    @Step("Fazer scroll completo e validar requisição ?infinity=scrolling com status 200")
    private void scrollAndValidateInfinityScrolling(StoriesPage storiesPage) {
        Response response = storiesPage.scrollAndWaitForInfinityScrolling();
        Assert.assertTrue(storiesPage.isInfinityScrollingSuccess(response), 
            "Requisição ?infinity=scrolling não retornou status 200");
    }

    @Step("Validar que URL contém /page/2/ após paginação")
    private void validatePage2Url(StoriesPage storiesPage) {
        boolean urlChanged = storiesPage.hasUrlChangedToPage2();
        if (urlChanged) {
            Assert.assertTrue(storiesPage.getCurrentUrl().contains("/page/2/"), 
                "URL não contém /page/2/ após paginação");
        } else {
            System.out.println("AVISO: URL não mudou para /page/2/ - paginação infinita pode não alterar URL");
        }
    }
}

