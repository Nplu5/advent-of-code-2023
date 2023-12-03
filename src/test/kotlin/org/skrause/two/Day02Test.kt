package org.skrause.two

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.skrause.two.Draw.Companion.blueDraw
import org.skrause.two.Draw.Companion.greenDraw
import org.skrause.two.Draw.Companion.redDraw
import org.skrause.two.DrawSet.Companion.drawSetOf
import org.skrause.utils.readInput
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import kotlin.test.Test

@DisplayName("Day 2")
class Day02Test {

    @Test
    @DisplayName("Can parse Game")
    fun parseGame() {
        // Arrange
        val input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        val expectedGame = Game(
            1, listOf(
                drawSetOf(
                    blueDraw(3),
                    redDraw(4)
                ),
                drawSetOf(
                    redDraw(1),
                    greenDraw(2),
                    blueDraw(6)
                ),
                drawSetOf(
                    greenDraw(2)
                )
            )
        )

        // Act
        val game = Game.parse(input)

        // Assert
        expectThat(game) {
            isEqualTo(expectedGame)
        }
    }

    @Test
    @DisplayName("Can parse Draw")
    fun parseDraw() {
        // Arrange
        val input = "4 blue"
        val expectedDraw = blueDraw(4)

        // Act
        val draw = Draw.parse(input)

        // Assert
        expectThat(draw).isEqualTo(expectedDraw)
    }

    @Test
    @DisplayName("Can parse DrawSet")
    fun parseDrawSet() {
        // Arrange
        val input = "3 blue, 3 red, 7 green"
        val expectedDrawSet = drawSetOf(
            redDraw(3),
            greenDraw(7),
            blueDraw(3)
        )

        // Act
        val drawSet = DrawSet.parse(input)

        // Assert
        expectThat(drawSet.draws) {
            contains(expectedDrawSet.draws)
        }
    }

    @Nested
    @DisplayName("Part 1")
    inner class Part1Test {
        @Test
        @DisplayName("Game breaks limit set")
        fun gameBreaksLimitSet() {
            // Arrange
            val input = listOf("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red")
            val limit = drawSetOf(
                redDraw(12),
                greenDraw(13),
                blueDraw(14)
            )

            // Act
            val result = sumOfBreakingGameIds(input, limit)

            // Assert
            expectThat(result).isEqualTo(0)
        }

        @Test
        @DisplayName("Game does not break limit set")
        fun gameFitsLimitSet() {
            // Arrange
            val input = listOf("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")
            val limit = drawSetOf(
                redDraw(12),
                greenDraw(13),
                blueDraw(14)
            )

            // Act
            val result = sumOfBreakingGameIds(input, limit)

            // Assert
            expectThat(result).isEqualTo(5)
        }

        @Test
        fun partOneTest() {
            // Arrange
            val input = readInput("Day02/part01-test")
            val limit = drawSetOf(
                redDraw(12),
                greenDraw(13),
                blueDraw(14)
            )

            // Act
            val result = sumOfBreakingGameIds(input, limit)

            // Assert
            expectThat(result).isEqualTo(8)
        }
    }

    @Nested
    @DisplayName("Part 2")
    inner class Part2Test {
        @Test
        fun partTwoTest() {
            // Arrange
            val input = readInput("Day02/part01-test")

            // Act
            val result = solvePart2(input)

            // Assert
            expectThat(result).isEqualTo(2286)
        }
    }
}