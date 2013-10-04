package de.m0ep.oauthtool;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class FacebookController {

    public static final String AUTH_URL = "https://www.facebook.com/dialog/oauth?scope=%s&redirect_uri=%s&client_id=%s";
    public static final String TOKEN_URL = "https://graph.facebook.com/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";;
    public static final String EXTENDED_URL = "https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id=%s&client_secret=%s&fb_exchange_token=%s";;

    private final FacebookDialog view;

    private static class FBCredentials {
        private String accessToken;
        private String extendedAccessToken;
        private int expires;

        public FBCredentials() {
            accessToken = null;
            extendedAccessToken = null;
            expires = 0;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken( String accessToken ) {
            this.accessToken = accessToken;
        }

        public String getExtendedAccessToken() {
            return extendedAccessToken;
        }

        public void setExtendedAccessToken( String extendedAccessToken ) {
            this.extendedAccessToken = extendedAccessToken;
        }

        public int getExpiresInSeconds() {
            return expires;
        }

        public void setExpiresInSeconds( int expires ) {
            this.expires = expires;
        }
    }

    public FacebookController( final FacebookDialog view ) {
        this.view = Preconditions.checkNotNull( view,
                "Required parameter view must be specified." );

        view.getGenerateButton().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                try {
                    FBCredentials credential = authorize(
                            view.getClientId().getText(),
                            view.getClientSecret().getText(),
                            view.getPermissons().getText() ) );

                    setCredentialData( credential );
                } catch ( IOException e1 ) {
                    JOptionPane.showMessageDialog(
                            view,
                            "Failed to get Accesstoke:\n" + e1.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE );
                }
            }
        } );

        view.getCloseButton().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                view.closeDialog();
            }
        } );
    }

    protected FBCredentials authorize( String id, String secret, String permissons )
            throws IOException {
        CodeReceiverServer receiverServer = new CodeReceiverServer();

        String redirectUri = receiverServer.getRedirectUlAndStartServer();

        URI uri = new URI( String
                .format( AUTH_URL, permissons, redirectUri, id ) );

        Desktop.getDesktop().browse( uri );

        String code = receiverServer.waitForCode();
        receiverServer.stopServer();

        /*
         * Get access token from authorization code
         */
        String accessTokenUri = String.format(
                TOKEN_URL,
                id,
                redirectUri,
                secret,
                code );

        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost( accessTokenUri );
        HttpResponse response = client.execute( postRequest );

        String body = EntityUtils.toString( response.getEntity() );
        List<NameValuePair> pairs = URLEncodedUtils.parse( body, Charset.defaultCharset() );
        FBCredentials result = new FBCredentials();

        for ( NameValuePair nvp : pairs ) {
            if ( "access_token".equals( nvp.getName() ) ) {
                result.setAccessToken( nvp.getValue() );
            } else if ( "".equals( nvp.getName() ) ) {
                result.setExpiresInSeconds( Integer.parseInt( nvp.getValue() ) );
            }
        }

        if ( null != result.getAccessToken() ) {
            /*
             * extend access token
             */
            postRequest = new HttpPost(
                    String.format(
                            EXTENDED_URL,
                            id,
                            secret,
                            result.getAccessToken() ) );

            response = client.execute( postRequest );

            System.out.println( response.getStatusLine().getStatusCode() );
            body = EntityUtils.toString( response.getEntity() );
            pairs = URLEncodedUtils.parse( body, Charset.defaultCharset() );

            for ( NameValuePair nvp : pairs ) {
                if ( "access_token".equals( nvp.getName() ) ) {
                    result.setAccessToken( nvp.getValue() );
                } else if ( "".equals( nvp.getName() ) ) {
                    result.setExpiresInSeconds( Integer.parseInt( nvp.getValue() ) );
                }
            }
        }

        return result;
    }

    protected void setCredentialData( FBCredentials credential ) {
        view.getAccesstoken().setText( credential.getAccessToken() );
        view.getExtendedToken().setText( credential.getExtendedAccessToken() );
        view.getExpires().setText( new Date( credential.getExpiresInSeconds() * 1000L
                + System.currentTimeMillis() ).toString() );
    }

    public void run() {
        if ( null != view ) {
            view.showDialog();
        }
    }
}
