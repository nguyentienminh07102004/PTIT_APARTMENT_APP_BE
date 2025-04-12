package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Document.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReportRepository extends MongoRepository<ReportDocument, String> {
}
