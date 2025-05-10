package com.wayon.SmartTransfer.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public enum FeeRange {
    SAME_DAY(0, 0, new BigDecimal("3.00"), new BigDecimal("0.025")),
    FROM_1_TO_10(1, 10, new BigDecimal("12.00"), BigDecimal.ZERO),
    FROM_11_TO_20(11, 20, BigDecimal.ZERO, new BigDecimal("0.082")),
    FROM_21_TO_30(21, 30, BigDecimal.ZERO, new BigDecimal("0.069")),
    FROM_31_TO_40(31, 40, BigDecimal.ZERO, new BigDecimal("0.047")),
    FROM_41_TO_50(41, 50, BigDecimal.ZERO, new BigDecimal("0.017"));

    private final int minDays;
    private final int maxDays;
    private final BigDecimal fixedFee;
    private final BigDecimal percentageFee;

    FeeRange(int minDays, int maxDays, BigDecimal fixedFee, BigDecimal percentageFee) {
        this.minDays = minDays;
        this.maxDays = maxDays;
        this.fixedFee = fixedFee;
        this.percentageFee = percentageFee;
    }

    public boolean appliesTo(int days) {
        return days >= minDays && days <= maxDays;
    }

    public BigDecimal calculateFee(BigDecimal amount) {
        return fixedFee.add(amount.multiply(percentageFee));
    }

    public static FeeRange fromDays(int days) throws IllegalArgumentException {
        return Arrays.stream(values())
                .filter(range -> range.appliesTo(days))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No fee range found for " + days + " days"));
    }

    public static int calculateDaysDifference(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
}
