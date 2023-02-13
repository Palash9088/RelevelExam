package pages;//import java.util.*;

import base.PredefinedActions;
import constants.ConstantPaths;
import org.openqa.selenium.WebElement;
import utils.PropertyReading;

import java.util.ArrayList;
import java.util.List;

public class GoIbiboHomepage extends PredefinedActions {
    private static GoIbiboHomepage goIbiboHomepage;
    private final PropertyReading goIbiboProp;
    private static String newMonth;

    private GoIbiboHomepage() {
        goIbiboProp = new PropertyReading(ConstantPaths.PROP_PATH + "GoIbiboProp.properties");
    }

    public static GoIbiboHomepage getGoIbiboHomepage() {
        if (goIbiboHomepage == null)
            goIbiboHomepage = new GoIbiboHomepage();
        return goIbiboHomepage;
    }

    public void closePopUp() {
        clickOnElement(goIbiboProp.getValue("firstPopUp"), true);
        clickOnElement(goIbiboProp.getValue("popUp"), true);
    }

    public void clickOnRoundTripBtn() {
        clickOnElement(goIbiboProp.getValue("roundTripRadio"), true);
    }

    public void enterSource(String sourceCity) {
        clickOnElement(goIbiboProp.getValue("sourceCity"), true);
        enterText(getElement(goIbiboProp.getValue("sourceCityEnterText"), true), sourceCity);
    }

    public List<String> getSourceCitiesList() {
        return getWebElementListInString(goIbiboProp.getValue("sourceList"), true);
    }

    public void clickOnFirstOptionInSource() {
        clickOnElement(goIbiboProp.getValue("firstCity"), true);
    }

    public void enterDestination(String destinationCity) {
        enterText(getElement(goIbiboProp.getValue("destinationCity"), true), destinationCity);
    }

    public List<String> getDestinationList() {
        return getWebElementListInString(goIbiboProp.getValue("destinationList"), true);
    }

    public void clickOnFirstDestination() {
        clickOnElement(goIbiboProp.getValue("destinationFirstCity"), true);
    }

    public void clickOnTodayDate() {
        clickOnElement(goIbiboProp.getValue("todayDate"), true);
    }

    public List<String> getListOfWeek() {
        List<String> weekList = new ArrayList<>();
        for (WebElement element : getWebElementList(goIbiboProp.getValue("weekList"), true)) {
            weekList.add(element.getAttribute("title"));
        }
        return weekList;
    }

    public List<String> getListOfWeekDestination() {
        List<String> weekList = new ArrayList<>();
        for (WebElement element : getWebElementList(goIbiboProp.getValue("weekListDestination"), true)) {
            weekList.add(element.getAttribute("title"));
        }
        return weekList;
    }

    public boolean isSelectDateEnabled() {
        return getAttribute(getElement(goIbiboProp.getValue("todayDate"), true), "aria-selected").contains("true");
    }

    public boolean isPreviousMonthDisabled() {
        return getAttribute(getElement(goIbiboProp.getValue("previousButton"), false), "class").contains("Disabled");
    }

    public void clickOnReturnDate() {
        clickOnElement(goIbiboProp.getValue("clickOnReturn"), true);
    }

    public String getCurrentMonthAndYear() {
        return getElementText(getElement(goIbiboProp.getValue("currentMonth"), true));
    }

    public void selectDestinationDate(String month, String date) {
        String newDate = month + " " + date;
        newMonth = String.format(goIbiboProp.getValue("selectDestinationDate"), newDate);

        String nextMonth = getElementText(getElement(goIbiboProp.getValue("nextMonth"), true));
        while (!nextMonth.contains(month)) {
            clickOnElement(goIbiboProp.getValue("nextButtonMonth"), true);
            nextMonth = getElementText(getElement(goIbiboProp.getValue("nextMonth"), true));
        }

        clickOnElement(newMonth, true);
    }

    public boolean isSelectDestinationDateEnabled() {
        return getAttribute(getElement(newMonth, true), "aria-selected").contains("true");
    }

    public String getReturnDate() {
        return getElementText(getElement(goIbiboProp.getValue("returnDate"), true)).substring(0, 1);
    }

    public boolean isPreviousAndNextMonthArrowPresent() {
        boolean flag = true;
        if (flag == true) {
            flag = getAttribute(getElement(goIbiboProp.getValue("backwardButton"), true), "aria-label").contains("Previous");
            flag = getAttribute(getElement(goIbiboProp.getValue("forwardButton"), true), "aria-label").contains("Next");
            return true;
        }
        return false;
    }

    public void clickOnDoneBtn() {
        clickOnElement(goIbiboProp.getValue("doneButton"), true);
    }

    public void clickOnAdultIncreaseButton(int num) {
        int count = 1;
        while (num > count) {
            clickOnElement(goIbiboProp.getValue("adultAddBtn"), true);
            count++;
        }
    }

    public void clickOnPremiumEconomyButton()
    {
        clickOnElement(goIbiboProp.getValue("premiumEconomyBtn"),true);
    }
    public boolean isPremiumClassSelected()
    {
        return getElement(goIbiboProp.getValue("premiumEconomyBtn"),true).isSelected();
    }
    public void clickOnDoneBtnClass()
    {
        clickOnElement(goIbiboProp.getValue("doneBtnClass"),true);
    }
    public String getTravellerInfo()
    {
        return getElementText(getElement(goIbiboProp.getValue("travellerInfo"),true));
    }
}
