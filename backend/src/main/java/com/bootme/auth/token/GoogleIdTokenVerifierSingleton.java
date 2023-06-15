package com.bootme.auth.token;

import com.bootme.common.exception.AuthenticationException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static com.bootme.common.exception.ErrorType.GOOGLE_LOGIN_FAIL;

public class GoogleIdTokenVerifierSingleton {
    private static GoogleIdTokenVerifier verifier = null;

    private GoogleIdTokenVerifierSingleton() {
    }

    /**
     * Verifies the specified Google ID token with the specified audience.
     *
     * @param googleAud the audience of the Google ID token
     * @param idToken the Google ID token to verify
     * @return the verified Google ID token
     */
    public static GoogleIdToken verifyGoogleIdToken(String googleAud, String idToken) {
        GoogleIdTokenVerifier verifier = getVerifier(googleAud);
        try {
            return verifier.verify(idToken);
        } catch (GeneralSecurityException | IOException e) {
            throw new AuthenticationException(GOOGLE_LOGIN_FAIL, idToken, e);
        }
    }

    /**
     * Gets a singleton instance of {@link GoogleIdTokenVerifier} with the specified audience.
     *
     * @param googleAud the audience of the Google ID token
     * @return a singleton instance of {@link GoogleIdTokenVerifier}
     */
    private static synchronized GoogleIdTokenVerifier getVerifier(String googleAud) {
        if (verifier == null) {
            HttpTransport transport = new NetHttpTransport();
            JsonFactory jsonFactory = new JacksonFactory();

            verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList(googleAud))
                    .build();
        }
        return verifier;
    }

}