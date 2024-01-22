package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GuestReservationsPage {
    private WebDriver driver;
    @FindBy(css=".guest-reservations-text")
    private WebElement reservationsText;
    @FindBy(css="#guest-reservation-card")
    private List<WebElement> reservations;



    public GuestReservationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        return (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(reservationsText, "Your Reservations"));
    }

    public void cancelReservation(String startDate, String endDate, String accommodation) throws NoSuchElementException{
        scrollToTop();
        WebElement foundReservation = null;

        while (foundReservation == null && !isPageScrolledToBottom()) {
            for (WebElement reservation : reservations) {
                String foundAccommodation = reservation.findElement(By.cssSelector(".guest-reservation-component-accommodation-name")).getText().trim();
                String status = reservation.findElement(By.cssSelector(".reservation-status-container > span")).getText().trim();
                String foundStartDate = reservation.findElement(By.cssSelector(".guest-reservation-component-start-date > span")).getText().trim();
                String foundEndDate = reservation.findElement(By.cssSelector(".guest-reservation-component-end-date > span")).getText().trim();
                if (status.equals("Accepted") && foundAccommodation.equals(accommodation) &&
                        foundStartDate.equals(startDate) && foundEndDate.equals(endDate)) {
                    foundReservation = reservation;
                    break;
                }
            }
            if (foundReservation == null) {
                scrollDown();
                try{
                    reservations = driver.findElements(By.cssSelector("#guest-reservation-card"));
                }catch(NoSuchElementException e){
                    throw e;
                }
            }
        }
        if(foundReservation != null){
            WebElement cancelButton = foundReservation.findElement(By.cssSelector("#cancel-button"));
            clickElementWithJs(cancelButton);
        }
    }

    public Boolean checkCancelled(String startDate, String endDate, String accommodation){
        scrollToTop();
        WebElement foundReservation = null;

        while (foundReservation == null && !isPageScrolledToBottom()) {
            for (WebElement reservation : reservations) {
                String foundAccommodation = reservation.findElement(By.cssSelector(".guest-reservation-component-accommodation-name")).getText().trim();
                String status = reservation.findElement(By.cssSelector(".reservation-status-container > span")).getText().trim();
                String foundStartDate = reservation.findElement(By.cssSelector(".guest-reservation-component-start-date > span")).getText().trim();
                String foundEndDate = reservation.findElement(By.cssSelector(".guest-reservation-component-end-date > span")).getText().trim();
                if (status.equals("Cancelled") && foundAccommodation.equals(accommodation) &&
                        foundStartDate.equals(startDate) && foundEndDate.equals(endDate)) {
                    foundReservation = reservation;
                    break;
                }
            }
            if (foundReservation == null) {
                scrollDown();
                reservations = driver.findElements(By.cssSelector("#guest-reservation-card"));

            }
        }
        return foundReservation != null;
    }


    public int numberOfCancelled(){
        int cancelled = 0;
        List<WebElement> allReservations = driver.findElements(By.cssSelector("#guest-reservation-card"));
        for (WebElement reservation : allReservations) {
            String status = reservation.findElement(By.cssSelector(".reservation-status-container > span")).getText().trim();

            if (status.equals("Cancelled")) {
                cancelled++;
            }
        }

        return cancelled;
    }

    public int numberOfApproved(){
        int approved = 0;
        List<WebElement> allReservations = driver.findElements(By.cssSelector("#guest-reservation-card"));
        for (WebElement reservation : allReservations) {
            String status = reservation.findElement(By.cssSelector(".reservation-status-container > span")).getText().trim();

            if (status.equals("Accepted")) {
                approved++;
            }
        }

        return approved;
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
