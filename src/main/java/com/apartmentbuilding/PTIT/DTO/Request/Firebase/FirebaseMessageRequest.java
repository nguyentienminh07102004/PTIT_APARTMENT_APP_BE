package com.apartmentbuilding.PTIT.DTO.Request.Firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirebaseMessageRequest {
    private String title;
    private String body;
    private String image;
    private String registrationToken;
}
