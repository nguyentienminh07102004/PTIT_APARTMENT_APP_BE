package com.apartmentbuilding.PTIT.DTO.Response.Notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private String id;
    private String title;
    private String content;
    private String image;
    private String sender;
    private Date createdDate;
    private Map<String, Boolean> users;
}
