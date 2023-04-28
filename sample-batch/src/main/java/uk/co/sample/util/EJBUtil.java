package uk.co.sample.util;

import static uk.co.sample.constant.SystemErrorCodeConstant.ERROR_SYSTEM;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import uk.co.sample.exception.ApplicationSystemException;

public class EJBUtil {

    private EJBUtil() {
    }

    //***** injection field *****
    //***** constructor *****
    //***** public method *****
    @SuppressWarnings("unchecked")
    public static <T> T lookupRemoteEJB(Class<T> remoteInterfaceClass) {
        T ejbRemote;
        try {
            InitialContext ctx = new InitialContext();
            ejbRemote = (T) ctx.lookup(remoteInterfaceClass.getName());
            return ejbRemote;
        } catch (NamingException e) {
            throw new ApplicationSystemException(ERROR_SYSTEM, e);
        }
    }
    //***** protected method *****
    //***** private method *****
    //***** call back method *****
    //***** getter and setter *****

}
