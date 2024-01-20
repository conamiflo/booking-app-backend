package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OwnerAccommodationsPage {
    private WebDriver driver;

    @FindBy(css=".company-name")
    private WebElement companyName;
    @FindBy(css=".edit-accommodation-btn")
    private WebElement editAccommodationButton;
    @FindBy(css="#accommodation-card")
    private List<WebElement> ownerAccommodations;

    public OwnerAccommodationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean isPageOpened() {
        boolean isOpened = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(editAccommodationButton,"Edit accommodation"));
        return isOpened;
    }

    public WebElement editAccommodationByName(String accommodationName) {
        scrollToTop();
        WebElement foundAccommodation = null;

        while (foundAccommodation == null && !isPageScrolledToBottom()) {
            for (WebElement accommodation : ownerAccommodations) {
                String name = accommodation.findElement(By.cssSelector(".accommodation-card-text span")).getText().trim();
                if (name.equals(accommodationName)) {
                    foundAccommodation = accommodation;
                    break;
                }
            }
            if (foundAccommodation == null) {
                scrollDown();
                ownerAccommodations = driver.findElements(By.cssSelector("#accommodation-card"));
            }
        }
        if(foundAccommodation != null){
            WebElement editButton = foundAccommodation.findElement(By.cssSelector(".edit-accommodation-btn"));
            clickElementWithJs(editButton);
        }
        return foundAccommodation;
    }

    private void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }
    private boolean isPageScrolledToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long windowHeight = (long) js.executeScript("return window.innerHeight;");
        long scrollHeight = (long) js.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");
        long scrollPosition = (long) js.executeScript("return window.scrollY;");

        return windowHeight + scrollPosition>= scrollHeight;
    }

    public void clickElementWithJs(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }
}
