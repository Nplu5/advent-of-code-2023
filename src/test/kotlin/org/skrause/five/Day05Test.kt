package org.skrause.five

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.skrause.utils.readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.test.Test

@DisplayName("Day 5")
class Day05Test {
    @Nested
    @DisplayName("Part 1")
    inner class Part1Test {
        @Test
        fun partOneTest() {
            // Arrange
            val input = readInput("Day05/part01-test")

            // Act
            val result = solvePart1(input)

            // Assert
            expectThat(result).isEqualTo(13)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2Test {
        @Test
        fun partTwoTest() {
            // Arrange
            val input = readInput("Day05/part01-test")

            // Act
            val result = solvePart2(input)

            // Assert
            expectThat(result).isEqualTo(30)
        }
    }
}