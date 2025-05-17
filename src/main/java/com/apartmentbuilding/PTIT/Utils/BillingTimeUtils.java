package com.apartmentbuilding.PTIT.Utils;

import java.time.LocalDate;

public class BillingTimeUtils {
    public static String getBillingTimeBefore(String billingTime){
        String[] monthYear = billingTime.split("/");
        LocalDate billingDateTimeBefore = LocalDate.of(Integer.parseInt(monthYear[1]), Integer.parseInt(monthYear[0]), 1).minusDays(1);
        return String.join("/", "%02d".formatted(billingDateTimeBefore.getMonth().getValue()), String.valueOf(billingDateTimeBefore.getYear()));
    }

    public static boolean isBillingTime(String time) {
        return time.matches("^(0?[1-9]|1[0-2])/([0-9]{4})$");
    }
}
