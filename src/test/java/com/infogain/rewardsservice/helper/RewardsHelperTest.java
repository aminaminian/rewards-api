package com.infogain.rewardsservice.helper;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardsHelperTest {
    @Test
    public void testCalculatePoints_amount_overHundred() {
        assertEquals(90, RewardsHelper.calculatePoints(new BigDecimal(120)));
    }

    @Test
    public void testCalculatePoints_amount_equalsHundred() {
        assertEquals(50, RewardsHelper.calculatePoints(new BigDecimal(100)));
    }

    @Test
    public void testCalculatePoints_amount_lessThanHundred() {
        assertEquals(20, RewardsHelper.calculatePoints(new BigDecimal(70)));
    }

    @Test
    public void testCalculatePoints_amount_equalsFifty() {
        assertEquals(0, RewardsHelper.calculatePoints(new BigDecimal(50)));
    }

    @Test
    public void testCalculatePoints_amount_lessThanFifty() {
        assertEquals(0, RewardsHelper.calculatePoints(new BigDecimal(20)));
    }
}