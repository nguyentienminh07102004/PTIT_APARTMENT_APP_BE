package com.apartmentbuilding.PTIT.WebSocket.Repository;

import com.apartmentbuilding.PTIT.WebSocket.Domains.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReportRepository extends MongoRepository<ReportDocument, String> {
}
