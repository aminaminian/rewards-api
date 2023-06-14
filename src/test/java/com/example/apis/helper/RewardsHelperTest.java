package com.example.apis.helper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RewardsHelperTest {

    @ParameterizedTest
    @MethodSource("rewardsAndAmountsContentProvider")
    public void testCalculatePoints(int expectedPoints, BigDecimal amount){
        assertEquals(expectedPoints, RewardsHelper.calculatePoints(amount));
    }
    static Stream<Arguments> rewardsAndAmountsContentProvider() {
        return Stream.of(
                arguments(90, new BigDecimal(120)),
                arguments(50, new BigDecimal(100)),
                arguments(20, new BigDecimal(70)),
                arguments(0, new BigDecimal(50)),
                arguments(0, new BigDecimal(20))
        );
    }
}