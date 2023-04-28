package uk.co.sample.ws.base;

import uk.co.sample.constant.BaseConstant;
import uk.co.sample.security.Operator;
import uk.co.sample.util.ProcessingTimeUtil;

public class WsOperatorUtil {

    private WsOperatorUtil() {
        // Do nothing
    }

    public static Operator createDefaultOperator() {

        Operator operator = new Operator();
        operator.setTracingId(BaseConstant.SYSTEM_USER);
        operator.setTimeZone(ProcessingTimeUtil.getSystemTimeZone());
        return operator;
    }

}
