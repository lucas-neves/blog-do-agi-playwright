package br.com.agibank.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Response;

public class StoriesPage {
    private final Page page;

    public StoriesPage(Page page) {
        this.page = page;
    }

    public Response scrollAndWaitForInfinityScrolling() {
        Response[] responseHolder = new Response[1];
        page.onResponse(response -> {
            if (response.url().contains("?infinity=scrolling")) {
                responseHolder[0] = response;
            }
        });
        int previousHeight = 0;
        int currentHeight = (int) page.evaluate("document.body.scrollHeight");
        int scrollAttempts = 0;
        while (previousHeight != currentHeight && scrollAttempts < 10) {
            previousHeight = currentHeight;
            page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
            page.waitForTimeout(1000);
            currentHeight = (int) page.evaluate("document.body.scrollHeight");
            scrollAttempts++;
        }
        return responseHolder[0];
    }

    public boolean hasUrlChangedToPage2() {
        return page.url().contains("/page/2/");
    }

    public String getCurrentUrl() {
        return page.url();
    }

    public boolean isInfinityScrollingSuccess(Response response) {
        return response != null && response.status() == 200;
    }
}
