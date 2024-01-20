package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.tests.student1;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.TestBase;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.EditAccommodationPage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.HomePage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.LoginPage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.OwnerAccommodationsPage;

import java.util.List;
import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class Test1 extends TestBase {

    @Test
    public void test() {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.goToLoginPage();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageOpened());

        loginPage.enterCredentials("owner@gmail.com","123");
        loginPage.login();

        assertTrue(homePage.isPageOpened());
        homePage.goToOwnerAccommodations();

        OwnerAccommodationsPage ownerAccommodationsPage = new OwnerAccommodationsPage(driver);
        assertTrue(ownerAccommodationsPage.isPageOpened());

        WebElement notFoundAccommodation = ownerAccommodationsPage.editAccommodationByName("not exists");
        assertNull(notFoundAccommodation);

        WebElement foundAccommodation = ownerAccommodationsPage.editAccommodationByName("asfasfasf");
        assertNotNull(foundAccommodation);

        EditAccommodationPage editAccommodationsPage = new EditAccommodationPage(driver);
        assertTrue(editAccommodationsPage.isPageOpened("asfasfasf"));

    }
}
