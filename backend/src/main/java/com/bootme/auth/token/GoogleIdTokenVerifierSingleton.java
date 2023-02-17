package com.bootme.auth.token;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

public class GoogleIdTokenVerifierSingleton {
    private static GoogleIdTokenVerifier verifier = null;

    private GoogleIdTokenVerifierSingleton() {}

    public static synchronized GoogleIdTokenVerifier getVerifier(String googleAud) {
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