package com.apartmentbuilding.PTIT.DTO.Response.Notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationUserResponse {
    private String id;
    private String title;
    private String content;
    private Date createdDate;
    private String sender;
    private Boolean isRead;
    private String image;
}
