package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.Report.ReportRequest;
import com.apartmentbuilding.PTIT.Model.Document.ReportDocument;
import com.apartmentbuilding.PTIT.Service.Report.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/reports")
public class ReportController {
    private final SimpMessagingTemplate messagingTemplate;
    private final IReportService reportService;

    @MessageMapping(value = "/notification")
    @SendTo(value = "/topic/notification")
    public ReportDocument sendReport(@Payload ReportRequest report) {
        return this.reportService.create(report, "Hello");
    }

    @MessageMapping(value = "/{email}")
    public void send(@DestinationVariable String email) {
        this.messagingTemplate.convertAndSendToUser(email, "/queue/receive-report", "Hello");
    }

    @MessageMapping(value = "/hello")
    public void sendToUser(Principal principal, @Payload ReportRequest report) {
        String dest = "nguyentienminhntm07102004@gmail.com";
        if (principal.getName().equals(dest)) {
            dest = "nguyentienminh07102004@gmail.com";
        }
        ReportDocument reportDocument = this.reportService.create(report, principal.getName());
        this.messagingTemplate.convertAndSendToUser(dest, "/queue/hello", reportDocument);
    }
}
