package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.Service.DataStatistics.IDataStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/data-statistics")
public class DataStatisticsController {
    private final IDataStatisticService dataStatisticService;

    @GetMapping(value = "/electric/top/{top}")
    public ResponseEntity<Map<String, Double>> statisticsForElectricInvoice(@PathVariable Integer top) {
        return ResponseEntity.status(HttpStatus.OK).body(this.dataStatisticService.statisticByElectricInvoice(top));
    }
}
