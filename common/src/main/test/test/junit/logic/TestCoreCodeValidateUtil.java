package test.junit.logic;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import uk.co.sample.constant.CodeValidateConstant;
import uk.co.sample.constant.SystemErrorCodeConstant;
import uk.co.sample.exception.ApplicationDataException;
import uk.co.sample.util.CoreCodeValidateUtil;
import uk.co.sample.util.NumericValidateBean;
import uk.co.sample.util.TextValidateBean;
import uk.co.sample.util.ValidateUtilHelper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateUtilHelper.class)
public class TestCoreCodeValidateUtil {

    private static final String ANY_DATABASE_ITEM = "";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isLengthBetween_pass() {

        // Prepare input parameters
        String text = "hello world";

        // Verify
        boolean result = CoreCodeValidateUtil.isLengthBetween(text, 1, 30);
        Assert.assertTrue(result);

    }

    @Test
    public void isLengthBetween_fail() {

        // Prepare input parameters
        String text = "very long long long long long long text";

        // Verify
        boolean result = CoreCodeValidateUtil.isLengthBetween(text, 1, 30);
        Assert.assertFalse(result);

    }

    @Test
    public void isValid_AlphaNumNoSpace_pass() {

        // Given/mock
        PowerMockito.replace(PowerMockito.method(ValidateUtilHelper.class, "obtainProperty")).with((proxy, method, args) -> mockTextValidateBeanForAlphaNumNoSpace());

        // Prepare input parameters
        String alphaNumNoSpace = "alphaNum123NoSpace";

        // Verify
        boolean result = CoreCodeValidateUtil.isValid(ANY_DATABASE_ITEM, alphaNumNoSpace);
        Assert.assertTrue(result);

    }

    @Test
    public void isValid_AlphaNumNoSpace_fail_containSpace() {

        // Given/mock
        PowerMockito.replace(PowerMockito.method(ValidateUtilHelper.class, "obtainProperty")).with((proxy, method, args) -> mockTextValidateBeanForAlphaNumNoSpace());

        // Prepare input parameters
        String alphaNumWithSpace = "alphaNumWith Space";

        // Verify
        boolean result = CoreCodeValidateUtil.isValid(ANY_DATABASE_ITEM, alphaNumWithSpace);
        Assert.assertFalse(result);

    }

    @Test
    public void isValid_AlphaNumNoSpace_fail_wrongValue() {

        // Given/mock
        PowerMockito.replace(PowerMockito.method(ValidateUtilHelper.class, "obtainProperty")).with((proxy, method, args) -> mockTextValidateBeanForAlphaNumNoSpace());

        // Prepare input parameters
        String alphaNumNoSpaceWrongValue = "alphaNumNoSpace@";

        // Verify
        boolean result = CoreCodeValidateUtil.isValid(ANY_DATABASE_ITEM, alphaNumNoSpaceWrongValue);
        Assert.assertFalse(result);

    }

    @Test
    public void isValid_AlphaNumNoSpace_fail_tooLong() {

        // Given/mock
        PowerMockito.replace(PowerMockito.method(ValidateUtilHelper.class, "obtainProperty")).with((proxy, method, args) -> mockTextValidateBeanForAlphaNumNoSpace());

        // Prepare input parameters
        String alphaNumNoSpaceTooLong = "alphaNumNoSpaceVeryLongLongLongLong";

        // Verify
        boolean result = CoreCodeValidateUtil.isValid(ANY_DATABASE_ITEM, alphaNumNoSpaceTooLong);
        Assert.assertFalse(result);

    }

    @Test
    public void isValid_Long_pass() {

        // Given/mock
        PowerMockito.replace(PowerMockito.method(ValidateUtilHelper.class, "obtainProperty")).with((proxy, method, args) -> mockNumericValidateBean());

        // Prepare input parameters
        String validNumber = "9223372036854775807";

        // Verify
        boolean result = CoreCodeValidateUtil.isValid(ANY_DATABASE_ITEM, validNumber);
        Assert.assertTrue(result);

    }

    @Test
    public void isValid_Long_fail_exceedMaxLong() {

        // Given/mock
        PowerMockito.replace(PowerMockito.method(ValidateUtilHelper.class, "obtainProperty")).with((proxy, method, args) -> mockNumericValidateBean());

        // Prepare input parameters
        String validNumber = "9223372036854775808";

        // Verify
        boolean result = CoreCodeValidateUtil.isValid(ANY_DATABASE_ITEM, validNumber);
        Assert.assertFalse(result);

    }

    @Test
    public void isValid_Long_invalidValue() {

        // Given/mock
        PowerMockito.replace(PowerMockito.method(ValidateUtilHelper.class, "obtainProperty")).with((proxy, method, args) -> mockNumericValidateBean());

        // Prepare input parameters
        String validNumber = "123abc";

        // Verify
        boolean result = CoreCodeValidateUtil.isValid(ANY_DATABASE_ITEM, validNumber);
        Assert.assertFalse(result);

    }

    @Test()
    public void isValid_long_invalidDefineProperties() {

        // Given/mock
        NumericValidateBean invalidDefintionProperties = new NumericValidateBean(CodeValidateConstant.LONG, "20", BigDecimal.ONE, new BigDecimal("99999999999999999999"));
        PowerMockito.replace(PowerMockito.method(ValidateUtilHelper.class, "obtainProperty")).with((proxy, method, args) -> invalidDefintionProperties);

        // Prepare input parameters
        String validNumber = "99999999999999999999";

        // Verify
        try {
            CoreCodeValidateUtil.isValid(ANY_DATABASE_ITEM, validNumber);
        } catch (ApplicationDataException e) {
            Assert.assertTrue(SystemErrorCodeConstant.ERROR_DEFINE_PROPERTY.equals(e.getCode()));
        }

    }

    private TextValidateBean mockTextValidateBeanForAlphaNumNoSpace() {
        return new TextValidateBean(CodeValidateConstant.ALPHA_NUM, 5, 30);
    }

    private NumericValidateBean mockNumericValidateBean() {
        return new NumericValidateBean(CodeValidateConstant.LONG, "19", BigDecimal.ONE, new BigDecimal(Long.MAX_VALUE));
    }

}
