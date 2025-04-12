package com.apartmentbuilding.PTIT.DTO.Response;

import com.apartmentbuilding.PTIT.Common.Enum.PaymentStatus;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
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
    private ApartmentEntity apartment;
    private Date paidDate;
    private String billingTime;
    private Date createdDate;
    private PaymentStatus status;
    private ElectricInvoiceResponse electricInvoice;
    private WaterInvoiceResponse waterInvoice;
    private List<VehicleInvoiceResponse> vehicleInvoices;
}