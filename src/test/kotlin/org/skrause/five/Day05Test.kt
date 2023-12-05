package org.skrause.five

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.skrause.utils.readInput
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.first
import strikt.assertions.isEqualTo
import kotlin.test.Test

@DisplayName("Day 5")
class Day05Test {

    @Test
    @DisplayName("Parse Seeds")
    fun parseSeeds() {
        // Arrange
        val input = "seeds: 79 14 55 13"

        // Act
        val seeds = extractSeeds(input)

        // Assert
        expectThat(seeds).contains(listOf(79, 14, 13, 55))
    }

    @Test
    @DisplayName("Parse a Mapping")
    fun parseMapping() {
        // Arrange
        val input = "50 98 2"
        val expectedMapping = Mapping(50, 98, 2)

        // Act
        val mapping = Mapping.parse(input)

        // Assert
        expectThat(mapping).isEqualTo(expectedMapping)
    }

    @Test
    @DisplayName("Can parse a mapping collection")
    fun parseMappingCollection() {
        // Arrange
        val input = """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent()
        val expectedMappingCollection = MappingCollection(
            "seed-to-soil",
            listOf(
                Mapping(50, 98, 2),
                Mapping(52, 50, 48)
            )
        )

        // Act
        val mappingCollection = MappingCollection.parse(input)

        // Assert
        expectThat(mappingCollection).isEqualTo(expectedMappingCollection)
    }

    @Test
    @DisplayName("Parse puzzle file")
    fun canParsePuzzleFile() {
        // Arrange
        val input = readInput("Day05/part01-test")

        // Act
        val result = parsePuzzleFile(input)

        // Assert
        expectThat(result.mappingCollections) {
            first().isEqualTo(
                MappingCollection(
                    "seed-to-soil",
                    listOf(
                        Mapping(50, 98, 2),
                        Mapping(52, 50, 48)
                    )
                )
            )
        }
    }

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