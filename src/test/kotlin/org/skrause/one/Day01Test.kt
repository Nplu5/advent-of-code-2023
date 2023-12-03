package org.skrause.one

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.skrause.utils.readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.test.Test

@DisplayName("Day 1")
class Day01Test {

    @Nested
    @DisplayName("Part 1")
    inner class Part1Test {
        @Test
        fun partOneTest() {
            // Arrange
            val input = readInput("Day01/part01-test.txt")

            // Act
            val result = solvePart1(input)

            // Assert
            expectThat(result).isEqualTo(142)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2Test {
        @Test
        fun partTwoTest() {
            // Arrange
            val input = readInput("Day01/part02-test")

            // Act
            val result = solvePart2(input)

            // Assert
            expectThat(result).isEqualTo(281)
        }

        @Test
        fun testLinesThatDiffer() {
            // Arrange
            val input = listOf(
                "plckgsixeight6eight95bnrfonetwonej",
                "2twonebs"
            )

            // Act
            val result = solvePart2(input)

            // Assert
            expectThat(result).isEqualTo(82)
        }

        @ParameterizedTest(name = "{index} - {0} should be org.skrause.dayone.replace parsed to {1}")
        @CsvSource(
            "plckgsixeight6eight95bnrfonetwonej,61",
            "2twonebs,21"
        )
        fun replaceDigitWordsWithNumber(lineToParse: String, expectedNumber: Int) {
            // Arrange
            val input = listOf(lineToParse)

            // Act
            val result = input.replaceSpelledOutDigits()
                .extractCalibrationCoordinates()

            // Assert
            expectThat(result).isEqualTo(expectedNumber)
        }
    }
}