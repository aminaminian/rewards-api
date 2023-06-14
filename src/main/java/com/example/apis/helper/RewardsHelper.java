package com.example.apis.helper;

import java.math.BigDecimal;

public class RewardsHelper {

    private static final BigDecimal SECOND_REWARDS_LEVEL = new BigDecimal(100);
    private static final BigDecimal FIRST_REWARDS_LEVEL = new BigDecimal(50);
    private static final BigDecimal SECOND_REWARDS_LEVEL_POINTS = new BigDecimal(2);

    public static int calculatePoints(BigDecimal amount) {
        var reminder = amount;
        var totalPoints = new BigDecimal(0);
        while (amountIsMoreThanFifty(reminder)) {
            if (amountIsMoreThanHundred(reminder)) {
                reminder = reminder.subtract(SECOND_REWARDS_LEVEL);
                totalPoints = totalPoints.add(reminder.multiply(SECOND_REWARDS_LEVEL_POINTS)).add(FIRST_REWARDS_LEVEL);
                break;
            } else if (amountIsMoreThanFifty(reminder)) {
                reminder = FIRST_REWARDS_LEVEL.subtract(reminder);
                totalPoints = totalPoints.add(reminder.abs());
            }
        }
        return totalPoints.intValue();
    }

    private static boolean amountIsMoreThanHundred(BigDecimal reminder) {
        return reminder.compareTo(SECOND_REWARDS_LEVEL) == 1;
    }

    private static boolean amountIsMoreThanFifty(BigDecimal reminder) {
        return reminder.compareTo(FIRST_REWARDS_LEVEL) == 1;
    }
}
