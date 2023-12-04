package org.skrause.four

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.skrause.utils.readInput
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import kotlin.test.Test

@DisplayName("Day 3")
class Day04Test {
    @Test
    @DisplayName("Can parse Scratchcard")
    fun parseScratchcard() {
        // Arrange
        val input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val expectedCard = Scratchcard(
            listOf(41, 48, 83, 86, 17),
            listOf(83, 86, 6, 31, 17, 9, 48, 53),
            1
        )

        // Act
        val parsedCard = Scratchcard.parse(input)

        // Assert
        expectThat(parsedCard).isEqualTo(expectedCard)
    }

    @Test
    @DisplayName("Returns list of winning numbers")
    fun returnWinningNumbers() {
        // Arrange
        val card = Scratchcard(
            listOf(41, 48, 83, 86, 17),
            listOf(83, 86, 6, 31, 17, 9, 48, 53),
            1
        )
        val expectedList = listOf(83, 48, 17, 86)

        // Act
        val result = card.matchingNumbers()

        // Assert
        expectThat(result).contains(expectedList)
    }

    @Nested
    @DisplayName("Part 1")
    inner class Part1Test {
        @Test
        fun partOneTest() {
            // Arrange
            val input = readInput("Day04/part01-test")

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
            val input = readInput("Day04/part01-test")

            // Act
            val result = solvePart2(input)

            // Assert
            expectThat(result).isEqualTo(30)
        }
    }
}