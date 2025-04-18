package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Document.RequestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRequestRepository extends MongoRepository<RequestDocument, String> {
}
