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
    public void test() throws InterruptedException {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.goToLoginPage();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageOpened());

        loginPage.enterCredentials("owner@gmail.com","123");
        loginPage.login();

        assertTrue(homePage.isPageOpened());
        homePage.goToOwnerAccommodations();
        homePage.closeProfileDialog(0,500);

        OwnerAccommodationsPage ownerAccommodationsPage = new OwnerAccommodationsPage(driver);
        assertTrue(ownerAccommodationsPage.isPageOpened());

        WebElement notFoundAccommodation = ownerAccommodationsPage.editAccommodationByName("not exists");
        assertNull(notFoundAccommodation);

        WebElement foundAccommodation = ownerAccommodationsPage.editAccommodationByName("asfasfasf");
        assertNotNull(foundAccommodation);

        EditAccommodationPage editAccommodationsPage = new EditAccommodationPage(driver);
        assertTrue(editAccommodationsPage.isPageOpened("asfasfasf"));

        // Cancellation days tests:

        editAccommodationsPage.enterCancellationDays("0");
        editAccommodationsPage.confirmEdit();
        assertTrue(editAccommodationsPage.isCancellationAlertDaysPopUp(false));

        editAccommodationsPage.enterCancellationDays("");
        editAccommodationsPage.confirmEdit();
        assertTrue(editAccommodationsPage.isCancellationAlertDaysPopUp(false));

        editAccommodationsPage.enterCancellationDays("-5124");
        editAccommodationsPage.confirmEdit();
        assertTrue(editAccommodationsPage.isCancellationAlertDaysPopUp(false));

        editAccommodationsPage.enterCancellationDays("3");
        editAccommodationsPage.confirmEdit();
        assertTrue(editAccommodationsPage.isCancellationAlertDaysPopUp(true));

        // Availability tests:

        editAccommodationsPage.enterAvailability("","");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("empty"));

        editAccommodationsPage.enterAvailability("","01/27/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("empty"));

        editAccommodationsPage.enterAvailability("01/26/2024","01/23/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("before"));

        editAccommodationsPage.enterAvailability("01/20/2024","01/27/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("beforeToday"));

        editAccommodationsPage.enterAvailability("01/26/2024","01/27/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("overlap"));

        editAccommodationsPage.enterAvailability("01/28/2024","01/29/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("reservation"));

        editAccommodationsPage.enterAvailability("01/30/2024","01/31/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("validAvailability"));
        assertTrue(editAccommodationsPage.isAvailabilityInTable("2024-01-30","2024-01-31"));

        editAccommodationsPage.removeAvailability("2024-01-30","2024-01-31");
        assertFalse(editAccommodationsPage.isAvailabilityInTable("2024-01-30","2024-01-31"));

        // For saving availability in database

//        editAccommodationsPage.enterAvailability("01/30/2024","01/31/2024");
//        assertTrue(editAccommodationsPage.isAvailabilityPopUp("validAvailability"));

        // Prices tests:

        editAccommodationsPage.enterPrices("50","","");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("empty"));

        editAccommodationsPage.enterPrices("50","","01/27/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("empty"));

        editAccommodationsPage.enterPrices("-521","01/26/2024","01/23/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("price"));

        editAccommodationsPage.enterPrices("0","01/26/2024","01/23/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("price"));

        editAccommodationsPage.enterPrices("","01/26/2024","01/23/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("price"));

        editAccommodationsPage.enterPrices("70","01/20/2024","01/27/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("beforeToday"));

        editAccommodationsPage.enterPrices("50","01/26/2024","01/23/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("before"));

        editAccommodationsPage.enterPrices("60","01/26/2024","01/27/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("overlapPrice"));

        editAccommodationsPage.enterPrices("60","01/30/2024","01/31/2024");
        assertTrue(editAccommodationsPage.isAvailabilityPopUp("validPriceAvailability"));
        assertTrue(editAccommodationsPage.isPriceInTable("2024-01-30","2024-01-31"));

        editAccommodationsPage.removePrice("2024-01-30","2024-01-31");
        assertFalse(editAccommodationsPage.isAvailabilityInTable("2024-01-30","2024-01-31"));

        // For saving everything in database:

//        editAccommodationsPage.enterAvailability("01/30/2024","01/31/2024");
//        assertTrue(editAccommodationsPage.isAvailabilityPopUp("validAvailability"));
//
//        editAccommodationsPage.enterPrices("60","01/30/2024","01/31/2024");
//        assertTrue(editAccommodationsPage.isAvailabilityPopUp("validPriceAvailability"));
//
//        editAccommodationsPage.enterCancellationDays("3");
//
//        editAccommodationsPage.confirmEdit();
//        assertTrue(editAccommodationsPage.isAvailabilityPopUp("Successfully edited accommodation!"));


    }
}
