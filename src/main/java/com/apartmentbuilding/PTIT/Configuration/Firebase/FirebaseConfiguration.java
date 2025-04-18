package com.apartmentbuilding.PTIT.Configuration.Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfiguration {

    @Bean(name = "firebaseAppConfiguration")
    FirebaseApp firebaseConfiguration() throws IOException {
        String pathToProjectDir = System.getProperty("user.dir");
        FileInputStream serviceAccount =
                new FileInputStream(pathToProjectDir + File.separator + "smart-condo-bb54b-firebase-adminsdk-fbsvc-df1600f5a8.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
