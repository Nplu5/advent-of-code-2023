package org.skrause.five

import org.skrause.utils.readInput

fun main() {
    val input = readInput("Day05/puzzle")
    // solvePart1(input).also { println("Part 1 result: $it") }
    solvePart2(input).also { println("part 2 result: $it") }
}

fun solvePart1(input: List<String>): Long {
    val file = parsePuzzleFile(input)
    val mappedSeeds = mapSeedsToLocation(file)
    return mappedSeeds.min()
}

fun mapSeedsToLocation(file: PuzzleFile): List<Seed> {
    return file.mappingCollections.fold(file.seeds) { list, mapping ->
        mapping.transform(list)
    }
}

private fun MappingCollection.transform(list: List<Seed>): List<Seed> {
    return list.map { mapValue(it) }
}

fun MappingCollection.mapValue(seed: Seed): Seed {
    val mapping = mappings.firstOrNull { it.contains(seed) }
    return mapping?.mapValue(seed) ?: seed
}

private fun Mapping.contains(seed: Seed): Boolean {
    return (sourceStart + this.numberOfSteps) > seed && seed >= sourceStart
}

private fun Mapping.mapValue(seed: Seed): Seed {
    return destinationStart + seed - sourceStart
}


fun solvePart2(input: List<String>): Long {
    val file = parsePuzzleFile(input, true)
    val mappedSeeds = mapSeedsToLocation(file)
    return mappedSeeds.min()
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

fun parsePuzzleFile(input: List<String>, useSeedRange: Boolean = false): PuzzleFile {
    val seeds = if (useSeedRange) {
        extractSeedRanges(input[0])
    } else {
        extractSeeds(input[0])
    }
    val mappingCollections = input.slice(IntRange(2, input.size - 1))
        .joinToString("|")
        .split("||")
        .map { it.split("|").joinToString("\n") }
        .map(MappingCollection::parse)
        .sortedWith(MappingComparator())
    return PuzzleFile(seeds, mappingCollections)
}

fun extractSeedRanges(seedLine: String): List<Seed> {
    return extractSeeds(seedLine).windowed(2,2){
        (it[0] until (it[0] + it[1])).map { number -> number }
    }.flatten()
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

typealias Seed = Long

fun extractSeeds(seedLine: String): List<Seed> = seedLine.split(": ")[1]
    .split(" ")
    .map(String::toLong)

data class Mapping(val destinationStart: Long, val sourceStart: Long, val numberOfSteps: Long) {

    companion object {
        fun parse(textToParse: String): Mapping {
            val (destination, source, range) = textToParse.split(" ")
                .map(String::toLong)
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