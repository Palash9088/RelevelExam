package pages;//import java.util.*;

import base.PredefinedActions;
import constants.ConstantPaths;
import utils.PropertyReading;

public class SipCalculatorPage extends PredefinedActions {
    private static SipCalculatorPage sipCalc;
    private final PropertyReading sipCalcProp;

    private SipCalculatorPage() {
        sipCalcProp = new PropertyReading(ConstantPaths.PROP_PATH + "SipCalculatorPageProp.properties");
    }

    public static SipCalculatorPage getSipCalcPage() {
        if (sipCalc == null)
            sipCalc = new SipCalculatorPage();
        return sipCalc;
    }

    public void enterValuesInInvestField(String amount) {
        clearElementField(sipCalcProp.getValue("amountField"), true);
        enterText(getElement(sipCalcProp.getValue("amountField"), true), amount);
    }

    public void clickOnCalculateBtn() {
        clickOnElement(sipCalcProp.getValue("calcBtn"), true);
    }

    public String getErrorMsg() {
        return getElementText(getElement(sipCalcProp.getValue("errorMsg"), true));
    }

    public void enterYears(String years) {
        clearElementField(sipCalcProp.getValue("yearField"), true);
        enterText(getElement(sipCalcProp.getValue("yearField"), true), years);
    }

    public String getMinYears() {
        return getAttribute(getElement(sipCalcProp.getValue("yearField"), true), "data-step");
    }

    public String getMaxYears() {
        return getAttribute(getElement(sipCalcProp.getValue("yearField"), true), "data-max");
    }

    public void hoverOnIcon() {
        hoverOnElement(getElement(sipCalcProp.getValue("tooltip"), true), true);
    }

    public String getTooltipMsg()
    {
        return getAttribute(getElement(sipCalcProp.getValue("tooltip"), true), "data-original-title");
    }
    public void customInvestmentCheck(String percentage){
        clickOnElement(sipCalcProp.getValue("strategy"),true);
        clickOnElement(sipCalcProp.getValue("custom"),true);
        clearElementField(sipCalcProp.getValue("clearCustom"),true);
        enterText(getElement(sipCalcProp.getValue("clearCustom"),true),percentage);

    }
    public void calculateFutureValue(String amount,String years,String returnPercentage)
    {
        enterValuesInInvestField(amount);
        enterYears(years);
        customInvestmentCheck(returnPercentage);
    }


}
