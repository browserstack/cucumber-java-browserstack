package com.browserstack.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage {
    private static WebDriver webDriver;

    public SearchPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private By searchTermField = By.name("q");
    private By submitSearch = By.id("_fZl");

    public void enterSearchTerm(String searchTerm) {
        webDriver.findElement(searchTermField).sendKeys(searchTerm);
    }

    public void submitSearch() {
        webDriver.findElement(submitSearch).click();
    }

}
