package com.apartmentbuilding.PTIT.Service.Firebase;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.Firebase.FirebaseMessageRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FirebaseServiceImpl implements IFirebaseService {
    private final FirebaseApp firebaseApp;

    @Override
    @Transactional
    public String sendMessage(FirebaseMessageRequest firebaseMessageRequest) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(firebaseMessageRequest.getTitle())
                    .setBody(firebaseMessageRequest.getBody())
                    .setImage(firebaseMessageRequest.getImage())
                    .build();
            Message message = Message.builder()
                    .setToken(firebaseMessageRequest.getRegistrationToken())
                    .setNotification(notification)
                    .build();
            return FirebaseMessaging.getInstance(firebaseApp).send(message);
        } catch (FirebaseMessagingException e) {
            throw new DataInvalidException(ExceptionVariable.TOKEN_INVALID);
        }
    }
}
