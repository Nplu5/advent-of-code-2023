package org.skrause.five

import org.skrause.utils.readInput

fun main() {
    val input = readInput("Day05/puzzle")
    solvePart1(input).also { println("Part 1 result: $it") }
    solvePart2(input).also { println("part 2 result: $it") }
}

fun solvePart1(input: List<String>): Int {
    val file = parsePuzzleFile(input)
    return 1
}

fun solvePart2(input: List<String>): Int {
    return 1
}

val orderMap = mapOf(
    "seed-to-soil" to 1,
    "soil-to-fertilizer" to 2,
    "fertilizer-to-water" to 3,
    "water-to-light" to 4,
    "light-to-temperature" to 5,
    "temperature-to-humidity" to 6,
    "humidity-to-location" to 7
)

fun parsePuzzleFile(input: List<String>): PuzzleFile {
    val seeds = extractSeeds(input[0])
    val mappingCollections = input.slice(IntRange(2, input.size - 1))
        .joinToString("|")
        .split("||")
        .map { it.split("|").joinToString("\n") }
        .map(MappingCollection::parse)
        .sortedWith(MappingComparator())
    return PuzzleFile(seeds, mappingCollections)
}

class MappingComparator : Comparator<MappingCollection> {
    override fun compare(o1: MappingCollection?, o2: MappingCollection?): Int {
        require(o1 != null) { "Mapping collection cannot be null" }
        require(o2 != null) { "Mapping collection cannot be null" }
        require(orderMap[o1.name] != null) { "Unknown mapping: ${o1.name}" }
        require(orderMap[o2.name] != null) { "Unknown mapping: ${o2.name}" }
        return orderMap[o1.name]!! - orderMap[o2.name]!!
    }
}

data class PuzzleFile(val seeds: List<Seed>, val mappingCollections: List<MappingCollection>)

typealias Seed = Int

fun extractSeeds(seedLine: String): List<Seed> = seedLine.split(": ")[1]
    .split(" ")
    .map { it.toInt() }

data class Mapping(val destinationStart: Int, val sourceStart: Int, val numberOfSteps: Int) {
    companion object {
        fun parse(textToParse: String): Mapping {
            val (destination, source, range) = textToParse.split(" ")
                .map(String::toInt)
            return Mapping(destination, source, range)
        }
    }
}

data class MappingCollection(val name: String, val mappings: List<Mapping>) {
    companion object {
        fun parse(textToParse: String): MappingCollection {
            val lines = textToParse.split(("\n"))
            val name = lines.first()
                .split(" ")
                .first()
            val mappings = lines.slice(IntRange(1, lines.size - 1))
                .map(Mapping::parse)
            return MappingCollection(name, mappings)
        }
    }
}