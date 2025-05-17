package com.apartmentbuilding.PTIT.DTO.Response;

import com.apartmentbuilding.PTIT.Common.Enum.PaymentMethod;
import com.apartmentbuilding.PTIT.Common.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyInvoiceResponse {
    private String id;
    private String apartmentName;
    private Date paymentDate;
    private String billingTime;
    private Date dueDate;
    private PaymentMethod method;
    private Date createdDate;
    private PaymentStatus status;
    private ElectricInvoiceResponse electricInvoice;
    private WaterInvoiceResponse waterInvoice;
    private List<ParkingInvoiceResponse> vehicleInvoices;
    private List<ServiceInvoiceResponse> serviceInvoices;
    private Double totalPrice;
}