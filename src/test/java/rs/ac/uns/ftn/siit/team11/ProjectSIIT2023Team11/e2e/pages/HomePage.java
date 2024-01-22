package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.ranges.Range;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
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

    @FindBy(xpath = "//input[@type='date' and contains(@placeholder, 'Check in')]")
    private WebElement checkInDate;
    @FindBy(xpath = "//input[@type='date' and contains(@placeholder, 'Check out')]")
    private WebElement checkOutDate;

    @FindBy(xpath = "//input[@type='text' and contains(@placeholder, 'Location')]")
    private WebElement locationInput;

    @FindBy(xpath = "//input[@type='range' and @min='1' and @max='12']")
    private WebElement numberOfGuestsRange;

    @FindBy(xpath = "//img[@alt='Vector1328']")
    private WebElement searchImage;

    @FindBy(xpath = "//div[@class ='cards-matbtn2' and img[@alt='Vector1328']]")
    private WebElement searchContainer;
    @FindBy(css=".accommodation-card-container")
    private List<WebElement> accommodationCardContainers;

    @FindBy(xpath = "//div[@class='cards-filtersmatinputslot']")
    private WebElement filterContainerButton;

    @FindBy(xpath = "//span[@class='mdc-button__label' and text()='Apply']")
    private WebElement applyButton;


    @FindBy(xpath = "//input[@id='mat-input-0']")
    private WebElement minimumPriceInputElement;
    @FindBy(xpath = "//input[@id='mat-input-1']")
    private WebElement maximumPriceInputElement;

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


    public void setCheckInDate(String date) {
        setInputValue(checkInDate, date);
    }

    public long getCheckInDateInSeconds() throws ParseException {
        String date = checkInDate.getAttribute("value");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(date);
        return parsedDate.getTime() / 1000;
    }

    public void setCheckOutDate(String date) {
        setInputValue(checkOutDate, date);
    }

    public long getCheckOutDateInSeconds() throws ParseException {
        String date = checkInDate.getAttribute("value");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(date);
        return parsedDate.getTime() / 1000;
    }

    public void setLocation(String location) {
        setInputValue(locationInput, location);
    }
    public String getLocation() {
        return locationInput.getAttribute("value");
    }
    public void setNumberOfGuests(Integer value) {

        for(int i = 0; i < 12; i ++){
            numberOfGuestsRange.sendKeys(Keys.ARROW_LEFT);
        }
        for(int i = 1; i < value; i ++){
            numberOfGuestsRange.sendKeys(Keys.ARROW_RIGHT);
        }
    }
    public int getNumberOfGuests() {
        String value = numberOfGuestsRange.getAttribute("value");
        return Integer.parseInt(value);
    }
    private void setInputValue(WebElement inputElement, String value) {
        inputElement.clear();
        inputElement.sendKeys(value);
    }

    public void setMaximumPrice(Integer value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled');", maximumPriceInputElement);
        maximumPriceInputElement.clear();
        maximumPriceInputElement.sendKeys(String.valueOf(value));

        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('disabled', '');", maximumPriceInputElement);
    }

    public String getMinimumPrice() {
        return minimumPriceInputElement.getAttribute("value");
    }

    public void setMinimumPrice(Integer value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled');", minimumPriceInputElement);
        minimumPriceInputElement.clear();
        minimumPriceInputElement.sendKeys(String.valueOf(value));

        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('disabled', '');", minimumPriceInputElement);
    }

    public String getMaximumPrice() {
        return maximumPriceInputElement.getAttribute("value");
    }

    public void clickOnSearchContainer() {
        searchContainer.click();
    }
    public void clickOnFilterContainer() {
        filterContainerButton.click();
    }
    public void clickOnApplyContainer() {
        applyButton.click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10L));
        wait.until(ExpectedConditions.invisibilityOf(maximumPriceInputElement));
    }
}
