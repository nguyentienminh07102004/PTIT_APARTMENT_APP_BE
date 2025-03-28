package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.WebSocket.Domains.ReportDocument;
import com.apartmentbuilding.PTIT.WebSocket.Service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final IReportService reportService;

    @MessageMapping(value = "/report/add")
    @SendTo(value = "/user/topic")
    public ReportDocument save(@RequestParam MultipartFile file) {
        return reportService.create(file);
    }

    @MessageMapping(value = "/user/{id}")
    @SendTo(value = "/user/topic")
    public ReportDocument findById(@PathVariable String id) {
        return reportService.findReportById(id);
    }

    @MessageMapping(value = "/report/addMessage")
    @SendTo(value = "/user/topic")
    public void save(@Payload String mess) {
        System.out.println(mess);
    }
}
