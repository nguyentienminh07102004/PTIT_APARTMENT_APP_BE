package com.apartmentbuilding.PTIT.Utils;

import java.time.LocalDate;

public class BillingTimeUtils {
    public static String getBillingTimeBefore(String billingTime){
        String[] monthYear = billingTime.split("/");
        LocalDate billingDateTimeBefore = LocalDate.of(Integer.parseInt(monthYear[1]), Integer.parseInt(monthYear[0]), 1).minusDays(1);
        return String.join("/", String.valueOf(billingDateTimeBefore.getMonth().getValue()), String.valueOf(billingDateTimeBefore.getYear()));
    }
}
