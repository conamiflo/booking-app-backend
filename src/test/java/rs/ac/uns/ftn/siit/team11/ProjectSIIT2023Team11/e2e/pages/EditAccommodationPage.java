package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EditAccommodationPage {

    private WebDriver driver;

    @FindBy(css=".page1-matinputslot")
    private WebElement accommodationNameInput;
    @FindBy(css="#accommodation-card")
    private List<WebElement> ownerAccommodations;

    public EditAccommodationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean isPageOpened(String accommodationName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement accName = wait.until(ExpectedConditions.visibilityOf(accommodationNameInput));
        String inputValue = accName.getAttribute("value").trim();
        return inputValue.equals(accommodationName);
    }

}
