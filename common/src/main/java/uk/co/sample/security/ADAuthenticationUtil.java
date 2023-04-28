package uk.co.sample.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;

import uk.co.sample.constant.CommonConstant;

public class ADAuthenticationUtil implements Serializable {

    private static final long serialVersionUID = 1460887108027510864L;

    // ***** injection field *****
    @Inject
    private Logger            logger;

    //***** constructor *****
    //***** public method *****
    /**
     * obtainAuthentication<br>
     * @param user
     * @param pass
     *
     * @return  boolean
     */
    public List<ADAuthenticationBean> obtainAuthentication(String user, String pass) {

        try {
            logger.info("Start Active Directory Authentication For User [{}]", user);
            List<String> servers = Arrays.asList(ADAuthConstant.SERVER.split("\\|"));
            List<ADAuthenticationBean> infoList = new ArrayList<>();
            for (String server : servers) {

                List<String> domains = Arrays.asList(ADAuthConstant.DOMAIN_VALUE.split("\\|"));

                for (String domain : domains) {

                    infoList = obtainActiveDirectory(user, pass, server, domain);

                    if (!infoList.isEmpty()) {
                        return infoList;
                    }
                }
            }

        } finally {
            logger.info("End Active Directory Authentication For User [{}]", user);
        }

        return new ArrayList<>();
    }

    //***** private method *****
    /**
     * obtainActiveDirectory<br>
     * @param user
     * @param pass
     * @param server
     * @param domainValue
     *
     * @return List<ADAuthenticationBean> [not empty:OK]
     */
    private List<ADAuthenticationBean> obtainActiveDirectory(String user, String pass, String server, String domainValue) {
        logger.info("Active Directory Authenticate By Server {} and Domain {}", domainValue, server);

        DirContext ctx = null;
        String reconstructDomainValue = reconstructDomain(domainValue);
        try {
            Hashtable<String, String> env = new Hashtable<>();
            String securityPrincipal = user + "@" + domainValue;
            String providerUrl = "ldap://" + server + "/" + reconstructDomainValue;
            env.put(Context.SECURITY_AUTHENTICATION, ADAuthConstant.AUTHENTICATION_METHOD);
            env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
            env.put(Context.SECURITY_CREDENTIALS, pass);
            env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(javax.naming.Context.PROVIDER_URL, providerUrl);
            logger.info("Active Directory Authenticate SECURITY_PRINCIPAL = {} and PROVIDER_URL = {}", securityPrincipal, providerUrl);

            ctx = new InitialDirContext(env);

            SearchControls scon = new SearchControls();
            scon.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<SearchResult> results = ctx.search(CommonConstant.EMPTY, "(&(objectClass=user)(sAMAccountName=" + user + "))", scon);

            return extractAuthenInfo(domainValue, results);

        } catch (AuthenticationException e) { /* NG(USER/PASSWORD error) */
            logger.warn("Active Directory Authenticate For User {}", user, e);
            return new ArrayList<>();
        } catch (NamingException e) { /* NG(Server Error) */
            logger.error("Active Directory Authenticate For User {}", user, e);
            return new ArrayList<>();
        } catch (Exception e) { /* NG(Java Error) */
            //e.printStackTrace();
        } finally {
            if (null != ctx) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    //Do nothing
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * reconstructDomain<br>
     *
     * @param domain
     * @return  String (example : asia.ad.address.com → DC=asia,DC=ad,DC=address,DC=com)
     */
    private String reconstructDomain(String domain) {
        StringBuilder builder = new StringBuilder();
        String[] binddns = domain.split("\\.");
        for (int i = 0, n = binddns.length; i < n; i++) {
            builder.append("DC=" + binddns[i]);
            if (i < n - 1) {
                builder.append(CommonConstant.COMMA);
            }
        }
        return builder.toString();
    }

    /**
     * extractAuthenInfo
     * @param domainValue String
     * @param results NamingEnumeration<SearchResult>
     * @return List<ADAuthenticationBean>
     * @throws NamingException
     */
    private List<ADAuthenticationBean> extractAuthenInfo(String domainValue, NamingEnumeration<SearchResult> results) throws NamingException {

        List<ADAuthenticationBean> infoList = new ArrayList<>();
        if (results == null) {
            return infoList;
        }

        while (results.hasMoreElements()) {
            SearchResult sr = results.nextElement();
            Attributes attrs = sr.getAttributes();

            ADAuthenticationBean info = new ADAuthenticationBean();

            if (domainValue.equals(ADAuthConstant.DOMAIN_BACKUP)) {
                if (attrs.get("description") != null) {
                    String[] description = ((String) (attrs.get("description")).get()).split(CommonConstant.COLON);
                    info.setUsrName(description.length > 0 ? description[0] : CommonConstant.EMPTY);
                }
            } else {
                if (attrs.get("name") != null) {
                    String name = (String) (attrs.get("name")).get();

                    int index = name.indexOf('(');
                    info.setUsrName(index < 0 ? name : name.substring(0, index));
                }
            }

            if (attrs.get("mail") != null) {
                info.setUsrMail((String) (attrs.get("mail")).get());
            }
            if (attrs.get("sAMAccountName") != null) {
                info.setUsrId((String) (attrs.get("sAMAccountName")).get());
            }

            infoList.add(info);
        }

        return infoList;
    }

}
