package com.apartmentbuilding.PTIT.WebSocket.Domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Getter
@Setter
public class UserDocument {
    @MongoId
    private String id;
    @Field(name = "nickname")
    @Indexed(unique = true)
    private String nickname;
    @Field(name = "fullName")
    private String fullName;
}
