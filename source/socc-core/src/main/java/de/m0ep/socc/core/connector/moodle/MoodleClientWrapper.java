
package de.m0ep.socc.core.connector.moodle;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

import org.ontoware.rdf2go.model.node.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.moodlews.soap.LoginReturn;
import de.m0ep.moodlews.soap.Mdl_soapserverBindingStub;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class MoodleClientWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(MoodleClientWrapper.class);

    private static final String MOODLEWS_PATH = "/wspp/service_pp2.php";
    private static final String MOODLE_WSDL_POSTFIX = "/wspp/wsdl2";

    private Mdl_soapserverBindingStub bindingStub;
    private String username;
    private String password;
    private int authClient;
    private String sessionKey;

    public MoodleClientWrapper(URI serviceEndpoint, String username, String password)
            throws AuthenticationException, IOException {
        this.bindingStub = new Mdl_soapserverBindingStub(
                serviceEndpoint.toString() + MOODLEWS_PATH,
                serviceEndpoint.toString() + MOODLE_WSDL_POSTFIX,
                false);
        tryLogin();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAuthClient() {
        return authClient;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * Calls a {@link Callable} with a MoodleWS function and tries to relogin if
     * the usersession is expired.
     * 
     * @param callable {@link Callable} with MoodleWS function call.
     * @return Result of the {@link Callable}.
     */
    public <T> T callMethod(final Callable<T> callable)
            throws IOException, AuthenticationException {
        Preconditions.checkNotNull(callable, "callable can not be null");
        T result = null;

        try {
            result = callable.call();

            if (null == result) {
                if (!isMoodleWSRunning()) {
                    throw new IOException("No connection to " + bindingStub.getURL());
                }

                if (isSessionMaybeExpired()) {
                    LOG.debug("try relogin and call method again");
                    tryLogin();
                    result = callable.call();
                }
            }
        } catch (Exception e) {
            throw new IOException(e);
        }

        return result;
    }

    /**
     * Check if the moodle webservice is running.
     * 
     * @return Returns true if MoodleWS is running, false otherwise.
     */
    private boolean isMoodleWSRunning() {
        if (null == bindingStub)
            return false;

        try {
            new URL(bindingStub.getURL()).openConnection().connect();
        } catch (IOException e) {
            LOG.warn(e.getMessage(), e);
            return false;
        }

        return true;
    }

    /**
     * @return Returns true if the webservice session in Moodle is maybe expired
     */
    private boolean isSessionMaybeExpired() {
        return 0 == bindingStub.get_my_id(authClient, sessionKey);
    }

    /**
     * Tries to login to Moodle.
     */
    private void tryLogin() throws IOException, AuthenticationException {
        if (null != sessionKey)
            bindingStub.logout(authClient, sessionKey);

        LoginReturn login = bindingStub.login(username, password);

        if (null == login) {
            if (isMoodleWSRunning()) {
                throw new AuthenticationException(
                        "Maybe the login parameter are invalid or there" +
                                " is no MoodleWS webservice.");
            }

            throw new IOException("Connection to " + bindingStub.getURL() + " failed.");
        }

        this.authClient = login.getClient();
        this.sessionKey = login.getSessionkey();
    }
}
