package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {


    private WebDriver driver;

    private static String PAGE_URL = "http://localhost:4200";
    @FindBy(css=".loginBtn")
    private WebElement logIn;
    @FindBy(css=".company-name")
    private WebElement companyName;
    @FindBy(css="#profile-image-home")
    private WebElement profileImage;
    @FindBy(css=".dialog-container")
    private WebElement dialogMenu;
    @FindBy(css="#my-accommodations")
    private WebElement myAccommodationsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }
    public void goToLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logInButton = wait.until(ExpectedConditions.visibilityOf(logIn));
        logInButton.click();
    }

    public boolean isPageOpened() {
        boolean isOpened = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(companyName, "Booking"));
        return isOpened;
    }

    public void openProfileMenu(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement profile = wait.until(ExpectedConditions.visibilityOf(profileImage));
        profile.click();
    }

    public void goToOwnerAccommodations(){

        openProfileMenu();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement myAccommodations = wait.until(ExpectedConditions.visibilityOf(myAccommodationsButton));
        myAccommodations.click();
    }



}
