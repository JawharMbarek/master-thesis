
package de.m0ep.socc.core.connector.google.youtube.v2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.UserProfileEntry;
import com.google.gdata.util.ServiceException;

import de.m0ep.sioc.service.auth.APIKey;
import de.m0ep.sioc.service.auth.Password;
import de.m0ep.sioc.service.auth.Username;
import de.m0ep.socc.core.exceptions.AuthenticationException;

public class YoutubeV2ClientWrapper {
    private YouTubeService service;
    private UserProfileEntry userProfile;

    public YoutubeV2ClientWrapper(APIKey apiKey, Username username, Password password)
            throws AuthenticationException, IOException {
        Preconditions.checkNotNull(apiKey,
                "Required parameter apiKey must be specified.");
        Preconditions.checkArgument(apiKey.hasValue(),
                "The parameter apiKey has no value");
        Preconditions.checkNotNull(username,
                "Required parameter username must be specified.");
        Preconditions.checkArgument(username.hasValue(),
                "The parameter username has no value");
        Preconditions.checkNotNull(password,
                "Required parameter password must be specified.");
        Preconditions.checkArgument(password.hasValue(),
                "The parameter password has no value");

        this.service = new YouTubeService("socc", apiKey.getValue());

        try {
            this.service.setUserCredentials(
                    username.getValue(),
                    password.getValue());
        } catch (com.google.gdata.util.AuthenticationException e) {
            throw new AuthenticationException(e);
        }

        try {
            this.userProfile = this.service.getEntry(
                    new URL("http://gdata.youtube.com/feeds/api/users/default?v=2"),
                    UserProfileEntry.class);
        } catch (MalformedURLException | ServiceException e) {
            Throwables.propagate(e);
        }
    }

    public YouTubeService getService() {
        return service;
    }

    public UserProfileEntry getUserProfile() {
        return userProfile;
    }
}
