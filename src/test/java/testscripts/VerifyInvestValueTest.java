package testscripts;//import java.util.*;

import base.PredefinedActions;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SipCalculatorPage;

public class VerifyInvestValueTest extends TestBase {
    static Logger log = Logger.getLogger(VerifyInvestValueTest.class);

    @Test
    public void verifyMinValue() {
        SipCalculatorPage sipCalculatorPage = getSipCalculatorPageObj();
        sipCalculatorPage.enterValuesInInvestField("499");
        sipCalculatorPage.clickOnCalculateBtn();
        log.info(sipCalculatorPage.getErrorMsg());
        Assert.assertEquals(sipCalculatorPage.getErrorMsg(), "Min Amount is 500");
        PredefinedActions.takeScreenshot("verifyMinValue");
    }

    @Test
    public void verifyMaxValue() {
        SipCalculatorPage sipCalculatorPage = getSipCalculatorPageObj();
        sipCalculatorPage.enterValuesInInvestField("50000001");
        sipCalculatorPage.clickOnCalculateBtn();
        log.info(sipCalculatorPage.getErrorMsg());
        Assert.assertEquals(sipCalculatorPage.getErrorMsg(), "Max Amount is 5 Crs");
        PredefinedActions.takeScreenshot("verifyMaxValue");

    }

    @Test
    public void verifyNoValue() {
        SipCalculatorPage sipCalculatorPage = getSipCalculatorPageObj();
        sipCalculatorPage.enterValuesInInvestField("");
        sipCalculatorPage.clickOnCalculateBtn();
        log.info(sipCalculatorPage.getErrorMsg());
        Assert.assertEquals(sipCalculatorPage.getErrorMsg(), "This field is required");
        PredefinedActions.takeScreenshot("verifyNoValue");
    }


    @Test
    public void minAndMaxYears() {
        SipCalculatorPage sipCalculatorPage = getSipCalculatorPageObj();
        log.info("Minimum Years is " + sipCalculatorPage.getMinYears());
        log.info("Maximum Years is " +sipCalculatorPage.getMaxYears());
    }

    @Test
    public void verifyIfZeroOrBlankYears() {
        SipCalculatorPage sipCalculatorPage = getSipCalculatorPageObj();
        sipCalculatorPage.enterYears("0");
        sipCalculatorPage.clickOnCalculateBtn();
        log.info(sipCalculatorPage.getErrorMsg());
    }
    @Test
    public void printToolTipMsg()
    {
        SipCalculatorPage sipCalculatorPage = getSipCalculatorPageObj();
        sipCalculatorPage.hoverOnIcon();
        PredefinedActions.takeScreenshot("printToolTipMsg");
        log.info(sipCalculatorPage.getTooltipMsg());
    }
    @Test
    public void verifyMinMaxValueInCustomField()
    {
        SipCalculatorPage sipCalculatorPage = getSipCalculatorPageObj();
        sipCalculatorPage.customInvestmentCheck("8.0");
    }
    @Test
    public void verifyFutureValue()
    {
        SipCalculatorPage sipCalculatorPage = getSipCalculatorPageObj();
        sipCalculatorPage.calculateFutureValue("10000","10","10");
        sipCalculatorPage.clickOnCalculateBtn();
        PredefinedActions.takeScreenshot("verifyFutureValue");
    }
}

