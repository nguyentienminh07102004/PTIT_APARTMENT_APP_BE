package com.apartmentbuilding.PTIT.Service.DataStatistics;

import java.util.Map;

public interface IDataStatisticService {
    Map<String, Double> statisticByElectricInvoice(Integer top);
    Map<String, Double> statisticByWaterInvoice();
    Map<String, Double> statisticByMonthInvoicePayment();
    Map<String, Double> statisticVehicleRegistration();

}
