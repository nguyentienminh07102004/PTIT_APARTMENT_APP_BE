package com.apartmentbuilding.PTIT.WebSocket.Domains;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

@Document(collection = "Reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDocument implements Serializable {
    @MongoId
    private String id;
    private String email;
    private Object content;

    public ReportDocument(String email, Object content) {
        this.email = email;
        this.content = content;
    }
}
