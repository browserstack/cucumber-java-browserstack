package com.browserstack.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver webDriver;

    private String selectedProductName;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.selectedProductName = "";
    }

    private By firstProductName = By.xpath("//*[@id=\"1\"]/p");

    private By firstProductAddToCartButton = By.xpath("//*[@id=\"1\"]/div[4]");

    private By cartPane = By.className("float-cart__content");

    private By productCartText = By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]");

    public void selectFirstProductName() {
        String firstProduct = webDriver.findElement(firstProductName).getText();
        setSelectedProductName(firstProduct);
    }

    public void clickAddToCartButton() {
        webDriver.findElement(firstProductAddToCartButton).click();
    }

    public void waitForCartToOpen() {
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartPane));
    }

    public String getProductCartText() {
        return webDriver.findElement(productCartText).getText();
    }

    public void setSelectedProductName(String selectedProductName) {
        this.selectedProductName = selectedProductName;
    }

    public String getSelectedProductName() {
        return selectedProductName;
    }
}
