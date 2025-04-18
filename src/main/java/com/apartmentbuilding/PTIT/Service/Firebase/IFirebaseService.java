package com.apartmentbuilding.PTIT.Service.Firebase;

import com.apartmentbuilding.PTIT.DTO.Request.Firebase.FirebaseMessageRequest;

public interface IFirebaseService {
    String sendMessage(FirebaseMessageRequest firebaseMessageRequest);
}
