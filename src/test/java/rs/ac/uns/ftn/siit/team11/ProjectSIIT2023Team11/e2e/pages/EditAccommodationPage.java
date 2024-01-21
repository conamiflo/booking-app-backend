package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
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
    @FindBy(css="button.page1-matbtn")
    private WebElement confirmEditButton;
    @FindBy(css="#cancellation-days-input")
    private WebElement cancellationDaysInput;
    @FindBy(css="#availability-from")
    private WebElement availabilityFromInput;
    @FindBy(css="#availability-to")
    private WebElement availabilityToInput;
    @FindBy(css="#price-from")
    private WebElement priceFromInput;
    @FindBy(css="#price-to")
    private WebElement priceToInput;
    @FindBy(css="#price-list-input")
    private WebElement priceForPriceListInput;
    @FindBy(css="#add-availability-btn")
    private WebElement addAvailabilityButton;
    @FindBy(css="#add-price-btn")
    private WebElement addPriceButton;

    public EditAccommodationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public boolean isPageOpened(String accommodationName) throws InterruptedException {
        scrollToTop();
//        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(accommodationNameInput));
        String inputValue = accommodationNameInput.getAttribute("value").trim();
        return inputValue.equals(accommodationName);
    }

    public void confirmEdit(){
        scrollDown("500");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(confirmEditButton));
        confirmEditButton.click();
    }


    public void enterCancellationDays(String days){
        scrollDown("500");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cancellationDays = wait.until(ExpectedConditions.visibilityOf(cancellationDaysInput));
        cancellationDays.clear(); cancellationDays.sendKeys(days);
    }

    public void enterAvailability(String firstDate, String secondDate) {
        scrollToTop();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(availabilityFromInput));
        wait.until(ExpectedConditions.visibilityOf(availabilityToInput));
        sendAvailabilityInputs(firstDate,secondDate);
    }

    public void enterPrices(String price,String firstDate, String secondDate) {
        scrollDown("1000");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(priceFromInput));
        wait.until(ExpectedConditions.visibilityOf(priceToInput));
        wait.until(ExpectedConditions.visibilityOf(priceForPriceListInput));
        sendPriceInputs(price,firstDate,secondDate);
    }

    public void sendAvailabilityInputs(String firstDate, String secondDate){
        resetDateInput(availabilityFromInput);
        resetDateInput(availabilityToInput);
        availabilityFromInput.sendKeys(firstDate);
        availabilityToInput.sendKeys(secondDate);
        addAvailabilityButton.click();
    }

    public void sendPriceInputs(String price,String firstDate, String secondDate){
        resetDateInput(priceFromInput);
        resetDateInput(priceToInput);
        priceForPriceListInput.clear();
        priceForPriceListInput.sendKeys(price);
        priceFromInput.sendKeys(firstDate);
        priceToInput.sendKeys(secondDate);
        addPriceButton.click();
    }
    public void resetDateInput(WebElement dateInput) {
        dateInput.clear();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].type = 'date';", dateInput);
    }
    public boolean isAlertPoppedUp(String alertMessage){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        if(alertText.equals(alertMessage)){
            alert.accept();
            return true;
        }
        return false;
    }

    public void removeAvailability(String firstDate, String secondDate){
        WebElement rowBeforeRemove = findRowByDates(firstDate,secondDate);
        WebElement removeButton = rowBeforeRemove.findElement(By.cssSelector("#remove-availability"));
        removeButton.click();
    }

    public void removePrice(String firstDate, String secondDate){
        WebElement rowBeforeRemove = findRowForPrices(firstDate,secondDate);
        WebElement removeButton = rowBeforeRemove.findElement(By.cssSelector("#remove-price"));
        removeButton.click();
    }

    public WebElement findRowByDates(String firstDate, String secondDate) {
        List<WebElement> rows = driver.findElements(By.cssSelector("#availability-table tbody tr:not(:first-of-type)"));
        for (WebElement row : rows) {
            String from = row.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
            String to = row.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
            if (from.equals(firstDate) && to.equals(secondDate)) {
                return row;
            }
        }
        return null;
    }

    public WebElement findRowForPrices(String firstDate, String secondDate) {
        List<WebElement> rows = driver.findElements(By.cssSelector("#price-table tbody tr:not(:first-of-type)"));
        for (WebElement row : rows) {
            String from = row.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
            String to = row.findElement(By.cssSelector("td:nth-child(3)")).getText().trim();
            if (from.equals(firstDate) && to.equals(secondDate)) {
                return row;
            }
        }
        return null;
    }

    public String dateErrors(String error){
        switch (error){
            case "before":
                return "Second date cant be before first!";
            case "beforeToday":
                return "Date cant be before today!";
            case "overlap":
                return "Availability is overlapping with existing availabilities!";
            case "overlapPrice":
                return "Price date is overlapping with existing price dates! ";
            case "reservation":
                return "There is a reservation in that period!";
            case "validAvailability":
                return "Successfully added new availability!";
            case "validPriceAvailability":
                return "Successfully added new price!";
            case "price":
                return "Price must be greater than 0!";
            case "empty":
                return "Date cant be empty!";
        }
        return error;
    }
    public boolean isAvailabilityPopUp(String error){
        return isAlertPoppedUp(dateErrors(error));
    }
    public boolean isCancellationAlertDaysPopUp(boolean valid){
        if(!valid) return isAlertPoppedUp("Cancellation days should be greater than 0!");
        return isAlertPoppedUp("Successfully edited accommodation!");
    }

    public boolean isAvailabilityInTable(String expectedFrom, String expectedTo) {
        List<WebElement> rows = driver.findElements(By.cssSelector("#availability-table tbody tr:not(:first-of-type)"));
        for (WebElement row : rows) {
            String from = row.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
            String to = row.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
            if (from.equals(expectedFrom) && to.equals(expectedTo)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPriceInTable(String expectedFrom, String expectedTo) {
        List<WebElement> rows = driver.findElements(By.cssSelector("#price-table tbody tr:not(:first-of-type)"));
        for (WebElement row : rows) {
            String from = row.findElement(By.cssSelector("td:nth-child(2)")).getText().trim();
            String to = row.findElement(By.cssSelector("td:nth-child(3)")).getText().trim();
            if (from.equals(expectedFrom) && to.equals(expectedTo)) {
                return true;
            }
        }
        return false;
    }
    public void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }

    private void scrollDown(String y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + y + ");");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void clickElementWithJs(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

}
