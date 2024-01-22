package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

public class TestBase{
    public static WebDriver driver;

    @BeforeSuite
    public void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @AfterSuite
    public void quitDriver() {
        driver.quit();
    }
}