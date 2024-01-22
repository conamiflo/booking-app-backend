package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
    @FindBy(css="#my-reservations")
    private WebElement guestReservationsButton;
    @FindBy(css="#log-out-button")
    private WebElement logOutButton;


    @FindBy(css=".accommodation-card-container")
    private List<WebElement> accommodationCardContainers;

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

    public void goToGuestReservations(){

        openProfileMenu();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement myReservations = wait.until(ExpectedConditions.visibilityOf(guestReservationsButton));
        myReservations.click();
    }
    public void goToLogOut(){

        openProfileMenu();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logOut = wait.until(ExpectedConditions.visibilityOf(logOutButton));
        logOut.click();
    }

    public List<Accommodation> getShownAccommodations(){
        List<Accommodation> accommodations = new ArrayList<>();

        for(WebElement webElement: accommodationCardContainers){
            WebElement location = webElement.findElement(By.xpath("./div/span[contains(@class, 'accommodation-card-text02')]/span"));
            String accommodationLocation = location.getText();
            WebElement price = webElement.findElement(By.xpath(".//span[@class='accommodation-card-text14']/span"));
            String accommodationPriceString = price.getText().toString().split(" ")[1];
            Double accommodationPrice = Double.parseDouble(accommodationPriceString);
            List<WebElement> numberOfGuests = webElement.findElements(By.xpath(".//div[contains(@class, 'accommodation-card-guests')]/span/span"));

            String minGuestsString = numberOfGuests.get(0).getText().split(" ")[2];
            Integer accommodationMinGuests = Integer.parseInt(minGuestsString);
            String maxGuestsString = numberOfGuests.get(1).getText().split(" ")[2];
            Integer accommodationMaxGuests = Integer.parseInt(maxGuestsString);

            Accommodation accommodation = Accommodation.builder().minGuests(accommodationMinGuests).maxGuests(accommodationMaxGuests).defaultPrice(accommodationPrice).location(accommodationLocation).build();
            accommodations.add(accommodation);
        }


        return accommodations;
    }



}
