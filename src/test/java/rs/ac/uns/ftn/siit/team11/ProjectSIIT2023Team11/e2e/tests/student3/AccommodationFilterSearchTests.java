package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.tests.student3;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.TestBase;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.EditAccommodationPage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.HomePage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.LoginPage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.OwnerAccommodationsPage;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class AccommodationFilterSearchTests extends TestBase {

    @Test
    public void test() {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.getShownAccommodations();


    }
}
