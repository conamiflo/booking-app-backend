package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage{

    private WebDriver driver;

    @FindBy(css=".login-emailinputslot.input")
    private WebElement emailInput;

    @FindBy(css=".login-passwordinputslot.input")
    private WebElement passwordInput;
    @FindBy(css="#login-button")
    private WebElement loginButton;

    @FindBy(css=".login-welcome-text")
    private WebElement welcomeText;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void enterCredentials(String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(emailInput));wait.until(ExpectedConditions.visibilityOf(passwordInput));
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
    }

    public boolean isPageOpened() {
        boolean isOpened = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(welcomeText, "Welcome"));
        return isOpened;
    }
    public void login() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();
    }


}
