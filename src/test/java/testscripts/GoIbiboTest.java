package testscripts;//import java.util.*;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.GoIbiboHomepage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoIbiboTest extends TestBase {

    @BeforeMethod
    public List<String> getWeekList()
    {
        ArrayList<String> weekList = new ArrayList<String>();
        weekList.add("Sunday");
        weekList.add("Monday");
        weekList.add("Tuesday");
        weekList.add("Wednesday");
        weekList.add("Thursday");
        weekList.add("Friday");
        weekList.add("Saturday");
        return weekList;
    }
    @BeforeMethod
    public String getCurrentMonth()
    {
        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy");
        String date = simpleDateFormat.format(date1);
        return date;
    }

    @Test
    public void verifyGoIbiboTrip() throws InterruptedException {
        GoIbiboHomepage goIbiboHomepage = getGoIbiboPageObj();
        goIbiboHomepage.closePopUp();
        goIbiboHomepage.clickOnRoundTripBtn();
        goIbiboHomepage.enterSource("Pun");
        Thread.sleep(800);
        System.out.println("---- Source List ----");
        System.out.println(goIbiboHomepage.getSourceCitiesList());
        goIbiboHomepage.clickOnFirstOptionInSource();

        System.out.println("---- Destination List ----");
        goIbiboHomepage.enterDestination("Del");
        Thread.sleep(500);
        System.out.println(goIbiboHomepage.getDestinationList());
        goIbiboHomepage.clickOnFirstDestination();
        goIbiboHomepage.clickOnTodayDate();
        Assert.assertTrue(goIbiboHomepage.isSelectDateEnabled());
        Assert.assertTrue(goIbiboHomepage.isPreviousMonthDisabled());
        Assert.assertEquals(goIbiboHomepage.getCurrentMonthAndYear(),getCurrentMonth());
        Assert.assertEquals(goIbiboHomepage.getListOfWeek(),getWeekList());
        goIbiboHomepage.clickOnReturnDate();
        String date = "01";
        goIbiboHomepage.selectDestinationDate("May",date);
        Assert.assertEquals(goIbiboHomepage.getReturnDate(),String.valueOf(date.charAt(1)));
        Assert.assertEquals(goIbiboHomepage.getListOfWeekDestination(),getWeekList());
        Assert.assertTrue(goIbiboHomepage.isSelectDestinationDateEnabled());
        Assert.assertTrue(goIbiboHomepage.isPreviousAndNextMonthArrowPresent());
        goIbiboHomepage.clickOnDoneBtn();
        int num  = 2;
        goIbiboHomepage.clickOnAdultIncreaseButton(num);
        goIbiboHomepage.clickOnPremiumEconomyButton();
        //Assert.assertTrue(goIbiboHomepage.isPremiumClassSelected());
        goIbiboHomepage.clickOnDoneBtnClass();
        Assert.assertEquals(goIbiboHomepage.getTravellerInfo(), 2 + " Adults");

    }
}
