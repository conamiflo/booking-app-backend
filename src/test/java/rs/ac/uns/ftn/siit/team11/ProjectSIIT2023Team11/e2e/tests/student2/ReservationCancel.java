package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.tests.student2;

import jakarta.transaction.Transactional;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.TestBase;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.GuestReservationsPage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.HomePage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.LoginPage;


import static org.junit.jupiter.api.Assertions.*;
import static org.testng.Assert.assertTrue;

@Transactional
public class ReservationCancel extends TestBase {

    private final String EMAIL = "g@gmail.com";
    private final String PASSWORD = "123";
    private final String START_DATE_VALID = "28-01-2024";
    private final String END_DATE_VALID = "30-01-2024";
    private final String ACCOMMODATION_ID_VALID = "2";
    private final String START_DATE_INVALID = "24-01-2024";
    private final String END_DATE_INVALID = "26-01-2024";
    private final String ACCOMMODATION_ID_INVALID = "4";

    @Test
    @Rollback
    public void test_valid() {
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.goToLoginPage();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageOpened());

        loginPage.enterCredentials(EMAIL,PASSWORD);
        loginPage.login();

        assertTrue(homePage.isPageOpened());
        homePage.goToGuestReservations();

        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        assertTrue(guestReservationsPage.isPageOpened());

        int beforeCancelled = guestReservationsPage.numberOfCancelled();
        int beforeApproved = guestReservationsPage.numberOfApproved();

        guestReservationsPage.cancelReservation(START_DATE_VALID, END_DATE_VALID, ACCOMMODATION_ID_VALID);

        assertTrue(guestReservationsPage.isPageOpened());

        assertTrue(guestReservationsPage.checkCancelled(START_DATE_VALID, END_DATE_VALID, ACCOMMODATION_ID_VALID));

        int afterCancelled = guestReservationsPage.numberOfCancelled();
        int afterApproved = guestReservationsPage.numberOfApproved();

        assertEquals((beforeCancelled+1), afterCancelled);
        assertEquals(beforeApproved, (afterApproved+1));

        homePage.goToLogOut();
    }

    @Test
    @Rollback
    public void test_cancellation_days_violation() {
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.goToLoginPage();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageOpened());

        loginPage.enterCredentials(EMAIL,PASSWORD);
        loginPage.login();

        assertTrue(homePage.isPageOpened());
        homePage.goToGuestReservations();

        GuestReservationsPage guestReservationsPage = new GuestReservationsPage(driver);
        assertTrue(guestReservationsPage.isPageOpened());

        int beforeCancelled = guestReservationsPage.numberOfCancelled();
        int beforeApproved = guestReservationsPage.numberOfApproved();

        assertThrows(NoSuchElementException.class,() -> guestReservationsPage.cancelReservation(START_DATE_INVALID, END_DATE_INVALID, ACCOMMODATION_ID_INVALID));


        assertTrue(guestReservationsPage.isPageOpened());

        int afterCancelled = guestReservationsPage.numberOfCancelled();
        int afterApproved = guestReservationsPage.numberOfApproved();

        assertEquals(beforeCancelled, afterCancelled);
        assertEquals(beforeApproved, afterApproved);


        homePage.goToLogOut();
    }
}
