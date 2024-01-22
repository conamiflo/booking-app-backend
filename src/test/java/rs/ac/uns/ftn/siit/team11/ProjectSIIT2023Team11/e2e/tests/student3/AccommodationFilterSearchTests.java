package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.tests.student3;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.TestBase;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.EditAccommodationPage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.HomePage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.LoginPage;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.e2e.pages.OwnerAccommodationsPage;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

public class AccommodationFilterSearchTests extends TestBase {
    private static final String VALID_START_DATE  = "01172024";
    private static final String VALID_END_DATE =  "01192024";
    private static final String INVALID_START_DATE  = "01172019";
    private static final String VALID_LOCATION  = "Novi Sad";
    private static final String INVALID_LOCATION =  "xgwae134";

    private static final Integer INVALID_MINIMUM_PRICE =  50;
    private static final Integer VALID_MINIMUM_PRICE =  30;
    private static final Integer INVALID_MAXIMUM_PRICE =  555;
    private static final Integer VALID_MAXIMUM_PRICE =  50;

    private static final Long START_DATE2  = LocalDateTime.of(2024, 1, 26, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE2 =  LocalDateTime.of(2024, 1, 29, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);


    @Test
    public void AccommodationsSearch_DoesDataMatchParams() throws ParseException {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.setCheckInDate(VALID_START_DATE);
        homePage.setCheckOutDate(VALID_END_DATE);
        homePage.setLocation(VALID_LOCATION);
        homePage.setNumberOfGuests(2);

        homePage.clickOnSearchContainer();

        homePage.getCheckInDateInSeconds();
        homePage.getCheckOutDateInSeconds();
        Integer numberOfGuests = homePage.getNumberOfGuests();
        String location = homePage.getLocation();

        List<Accommodation> accommodationList = homePage.getShownAccommodations();

        for(Accommodation accommodation : accommodationList){
            assertTrue(accommodation.getLocation().contains(location));
            assertTrue(accommodation.getMinGuests() <= numberOfGuests && accommodation.getMaxGuests() >= numberOfGuests);
        }
    }

    @Test
    public void AccommodationsSearch_DoesNotMatchParams() throws ParseException {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.setCheckInDate(INVALID_START_DATE);
        homePage.setCheckOutDate(VALID_END_DATE);
        homePage.setLocation(VALID_LOCATION);
        homePage.setNumberOfGuests(2);

        homePage.clickOnSearchContainer();

        homePage.getCheckInDateInSeconds();
        homePage.getCheckOutDateInSeconds();
        Integer numberOfGuests = homePage.getNumberOfGuests();
        String location = homePage.getLocation();

        List<Accommodation> accommodationList = homePage.getShownAccommodations();

        assertTrue(accommodationList.isEmpty());
    }

    @Test
    public void AccommodationsFilter_DoesDataMatchParams() {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.clickOnFilterContainer();
        homePage.setMinimumPrice(VALID_MINIMUM_PRICE);
        homePage.setMaximumPrice(VALID_MAXIMUM_PRICE);
        Integer minimumPrice = Integer.valueOf(homePage.getMinimumPrice());
        Integer maximumPrice = Integer.valueOf(homePage.getMaximumPrice());

        homePage.clickOnApplyContainer();



        List<Accommodation> accommodationList = homePage.getShownAccommodations();

        for(Accommodation accommodation : accommodationList){
            assertTrue(accommodation.getDefaultPrice()>=minimumPrice && accommodation.getDefaultPrice() <= maximumPrice);
        }
    }

    @Test
    public void AccommodationsFilter_DoesNotMatchParams() {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.clickOnFilterContainer();
        homePage.setMinimumPrice(INVALID_MINIMUM_PRICE);
        homePage.setMaximumPrice(INVALID_MAXIMUM_PRICE);

        homePage.clickOnApplyContainer();



        List<Accommodation> accommodationList = homePage.getShownAccommodations();
        assertTrue(accommodationList.isEmpty());

    }

    @Test
    public void AccommodationsCombined_DoesDataMatchParams() throws ParseException {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.setCheckInDate(VALID_START_DATE);
        homePage.setCheckOutDate(VALID_END_DATE);
        homePage.setLocation(VALID_LOCATION);
        homePage.setNumberOfGuests(2);

        homePage.clickOnSearchContainer();

        homePage.getCheckInDateInSeconds();
        homePage.getCheckOutDateInSeconds();
        Integer numberOfGuests = homePage.getNumberOfGuests();
        String location = homePage.getLocation();

        homePage.clickOnFilterContainer();
        homePage.setMinimumPrice(VALID_MINIMUM_PRICE);
        homePage.setMaximumPrice(VALID_MAXIMUM_PRICE);
        Integer minimumPrice = Integer.valueOf(homePage.getMinimumPrice());
        Integer maximumPrice = Integer.valueOf(homePage.getMaximumPrice());

        homePage.clickOnApplyContainer();


        List<Accommodation> accommodationList = homePage.getShownAccommodations();

        for(Accommodation accommodation : accommodationList){
            assertTrue(accommodation.getLocation().contains(location));
            assertTrue(accommodation.getMinGuests() <= numberOfGuests && accommodation.getMaxGuests() >= numberOfGuests);
            assertTrue(accommodation.getDefaultPrice()>=minimumPrice && accommodation.getDefaultPrice() <= maximumPrice);
        }
    }




    @Test
    public void AccommodationsCombined_InvalidSearchNoDataReturned() throws ParseException {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.setCheckInDate(INVALID_START_DATE);
        homePage.setCheckOutDate(VALID_END_DATE);
        homePage.setLocation(VALID_LOCATION);
        homePage.setNumberOfGuests(2);

        homePage.clickOnSearchContainer();

        homePage.getCheckInDateInSeconds();
        homePage.getCheckOutDateInSeconds();
        Integer numberOfGuests = homePage.getNumberOfGuests();
        String location = homePage.getLocation();

        homePage.clickOnFilterContainer();
        homePage.setMinimumPrice(VALID_MINIMUM_PRICE);
        homePage.setMaximumPrice(VALID_MAXIMUM_PRICE);
//        Integer minimumPrice = Integer.valueOf(homePage.getMinimumPrice());
//        Integer maximumPrice = Integer.valueOf(homePage.getMaximumPrice());

        homePage.clickOnApplyContainer();


        List<Accommodation> accommodationList = homePage.getShownAccommodations();

        assertTrue(accommodationList.isEmpty());


    }

    @Test
    public void AccommodationsCombined_InvalidFilterNoDataReturned() throws ParseException {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.setCheckInDate(VALID_START_DATE);
        homePage.setCheckOutDate(VALID_END_DATE);
        homePage.setLocation(VALID_LOCATION);
        homePage.setNumberOfGuests(2);

        homePage.clickOnSearchContainer();

        homePage.getCheckInDateInSeconds();
        homePage.getCheckOutDateInSeconds();
        Integer numberOfGuests = homePage.getNumberOfGuests();
        String location = homePage.getLocation();

        homePage.clickOnFilterContainer();
        homePage.setMinimumPrice(INVALID_MINIMUM_PRICE);
        homePage.setMaximumPrice(INVALID_MAXIMUM_PRICE);
//        Integer minimumPrice = Integer.valueOf(homePage.getMinimumPrice());
//        Integer maximumPrice = Integer.valueOf(homePage.getMaximumPrice());

        homePage.clickOnApplyContainer();


        List<Accommodation> accommodationList = homePage.getShownAccommodations();

        assertTrue(accommodationList.isEmpty());


    }


    @Test
    public void AccommodationsCombined_InvalidBothNoDataReturned() throws ParseException {

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened());

        homePage.setCheckInDate(INVALID_START_DATE);
        homePage.setCheckOutDate(VALID_END_DATE);
        homePage.setLocation(VALID_LOCATION);
        homePage.setNumberOfGuests(2);

        homePage.clickOnSearchContainer();

        homePage.getCheckInDateInSeconds();
        homePage.getCheckOutDateInSeconds();
        Integer numberOfGuests = homePage.getNumberOfGuests();
        String location = homePage.getLocation();

        homePage.clickOnFilterContainer();
        homePage.setMinimumPrice(INVALID_MINIMUM_PRICE);
        homePage.setMaximumPrice(INVALID_MAXIMUM_PRICE);
        Integer minimumPrice = Integer.valueOf(homePage.getMinimumPrice());
        Integer maximumPrice = Integer.valueOf(homePage.getMaximumPrice());

        homePage.clickOnApplyContainer();


        List<Accommodation> accommodationList = homePage.getShownAccommodations();

        assertTrue(accommodationList.isEmpty());


    }
}
