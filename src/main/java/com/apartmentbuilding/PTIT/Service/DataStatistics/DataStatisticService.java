package com.apartmentbuilding.PTIT.Service.DataStatistics;

import com.apartmentbuilding.PTIT.Mapper.ElectricInvoice.ElectricConvertor;
import com.apartmentbuilding.PTIT.Mapper.MonthlyInvoice.MonthlyInvoiceConvertor;
import com.apartmentbuilding.PTIT.Mapper.WaterInvoice.WaterConvertor;
import com.apartmentbuilding.PTIT.Repository.IElectricRepository;
import com.apartmentbuilding.PTIT.Repository.IMonthlyInvoiceRepository;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataStatisticService implements IDataStatisticService {
    private final IMonthlyInvoiceRepository monthlyInvoiceRepository;
    private final IElectricRepository electricRepository;
    private final IMonthlyInvoiceRepository waterInvoiceRepository;
    private final ElectricConvertor electricConvertor;
    private final WaterConvertor waterConvertor;
    private final MonthlyInvoiceConvertor monthlyInvoiceConvertor;

    @Override
    public Map<String, Double> statisticByElectricInvoice(Integer top) {
        if (top == null || top < 1) {
            top = 10;
        }
        Map<String, Double> res = new HashMap<>();
        List<String> top10BillingTimes = this.waterInvoiceRepository.findAllBillingTimeLast(top);
        Map<String, Double> data = new HashMap<>();
        for (String billingTime : top10BillingTimes) {
            Double price = data.getOrDefault(billingTime, null);
            if (price == null) {
                price = this.electricRepository.findTotalPriceByMonthlyInvoice_BillingTime(billingTime);
                data.put(billingTime, price);
            }
            String billingTimeBefore = BillingTimeUtils.getBillingTimeBefore(billingTime);
            Long countByBillingTimeBefore = this.monthlyInvoiceRepository.countByBillingTime(billingTimeBefore);
            Double priceBefore = data.getOrDefault(billingTimeBefore, null);
            if (priceBefore == null) {
                if (countByBillingTimeBefore == null || countByBillingTimeBefore <= 0) {
                    priceBefore = 0.0;
                } else {
                    priceBefore = this.electricRepository.findTotalPriceByMonthlyInvoice_BillingTime(billingTime);
                }
                data.put(billingTimeBefore, priceBefore);
            }
            res.put(billingTime, price - priceBefore);
        }
        return res;
    }

    @Override
    public Map<String, Double> statisticByWaterInvoice() {
        return Map.of();
    }

    @Override
    public Map<String, Double> statisticByMonthInvoicePayment() {
        return Map.of();
    }

    @Override
    public Map<String, Double> statisticVehicleRegistration() {
        return Map.of();
    }
}
