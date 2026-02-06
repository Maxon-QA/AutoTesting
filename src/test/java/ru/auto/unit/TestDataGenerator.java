package ru.auto.unit;

import java.util.stream.Stream;

public class TestDataGenerator {

    public static Stream<Integer> getGradePositive() {
        return Stream.of(2,3,4,5);
    }

    public static Stream<Integer> getGradeNegative() {
        return Stream.of(0,1,6,7);
    }

}
