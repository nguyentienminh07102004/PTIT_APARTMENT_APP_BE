package com.apartmentbuilding.PTIT.Model.Document;

import com.apartmentbuilding.PTIT.Common.Enum.RequestStatus;
import com.apartmentbuilding.PTIT.Common.Enum.RequestType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document(collection = "Reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDocument {
    @MongoId
    private String id;
    private String email;
    private String apartmentId;
    private String title;
    private Object content;
    private RequestType type;
    private RequestStatus status;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
