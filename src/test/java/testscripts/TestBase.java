package testscripts;//import java.util.*;

import base.PredefinedActions;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.GoIbiboHomepage;
import pages.SipCalculatorPage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBase {
    private static SipCalculatorPage sipCalculatorPage;
    private static GoIbiboHomepage goIbiboHomepage;

    @BeforeClass
    public void beforeClass() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        System.setProperty("current.date.time", sdf.format(new Date()));
        PropertyConfigurator.configure("src/main/resources/Configs/Log4j.properties");
    }

    @BeforeMethod
    public void openBrowser() {
        PredefinedActions.initializeBrowser("https://www.goibibo.com/", "chrome");
    }

    SipCalculatorPage getSipCalculatorPageObj() {
        if (sipCalculatorPage == null)
            sipCalculatorPage = SipCalculatorPage.getSipCalcPage();
        return sipCalculatorPage;
    }

    GoIbiboHomepage getGoIbiboPageObj() {
        if (goIbiboHomepage == null)
            goIbiboHomepage = GoIbiboHomepage.getGoIbiboHomepage();
        return goIbiboHomepage;
    }


    //@AfterMethod
    public void closeBrowser(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE)
            PredefinedActions.takeScreenshot(result.getName());
        PredefinedActions.closeBrowser();
    }
}
