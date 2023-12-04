package org.skrause.three

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.skrause.utils.readInput
import strikt.api.expectThat
import strikt.assertions.*
import kotlin.test.Test

@DisplayName("Day 3")
class Day03Test {
    @Test
    @DisplayName("Extracts correct numbers from line")
    fun extractNumbers() {
        // Arrange
        val input = "467..114.."
        val expectedResult = listOf(
            Number(467, IntRange(0, 2), 1),
            Number(114, IntRange(5, 7), 1)
        )

        // Act
        val result = parseNumbers(input, 1)

        // Assert
        expectThat(result).isEqualTo(expectedResult)
    }

    @Test
    @DisplayName("Find all Symbols")
    fun findSymbols() {
        // Arrange
        val input = listOf(
            "467..114..",
            "...*......",
            "..35..633."
        )

        // Act
        val cartesianSymbols = findAllSymbols(input)

        // Assert
        expectThat(cartesianSymbols) {
            hasSize(1)
            first().isEqualTo(CoordinateSymbol(3, 1, '*'))
        }
    }

    @Nested
    @DisplayName("Part 1")
    inner class Part1Test {
        @Test
        fun partOneTest() {
            // Arrange
            val input = readInput("Day03/part01-test")

            // Act
            val result = solvePart1(input)

            // Assert
            expectThat(result).isEqualTo(4361)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2Test {
        @Test
        fun partTwoTest() {
            // Arrange
            val input = readInput("Day03/part01-test")

            // Act
            val result = solvePart2(input)

            // Assert
            expectThat(result).isEqualTo(467835)
        }
    }
}