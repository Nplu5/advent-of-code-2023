package org.skrause.three

import org.skrause.utils.readInput

fun solvePart1(input: List<String>): Int {
    val symbols = findAllSymbols(input)
    val numbers = input.mapIndexed { yIndex, line ->
        yIndex to parseNumbers(line, yIndex)
    }.toMap()
    return symbols.flatMap { symbol -> numbers.findAdjacentTo(symbol) }
        .sumOf { it.number }
}

private fun Map<Int, List<Number>>.findAdjacentTo(symbol: CoordinateSymbol): List<Number> {
    val potentialNumbers = when (symbol.yIndex) {
        0 -> listOf(this[0], this[1])
        this.size -> listOf(this[symbol.yIndex - 1], this[symbol.yIndex])
        else -> listOf(this[symbol.yIndex - 1], this[symbol.yIndex], this[symbol.yIndex + 1])
    }
    return potentialNumbers.filterNotNull()
        .flatMap { numbers -> numbers.filter { number -> number.adjacentTo(symbol) } }
}

private fun Number.adjacentTo(symbol: CoordinateSymbol): Boolean {
    return xIndexRange.contains(symbol.xIndex) ||
            xIndexRange.contains(symbol.xIndex - 1) ||
            xIndexRange.contains(symbol.xIndex + 1)
}

fun solvePart2(input: List<String>): Int {
    val starSymbols = findAllSymbols(input)
        .filter { it.symbol == '*' }
    val numbers = input.mapIndexed { yIndex, line ->
        yIndex to parseNumbers(line, yIndex)
    }.toMap()
    return starSymbols.map { numbers.findAdjacentTo(it) }
        .filter { it.size == 2 }
        .map { it[0].number * it[1].number }
        .sumOf { it }
}

fun main() {
    val input = readInput("Day03/puzzle")
    solvePart1(input).also { println("Part 1 result: $it") }
    solvePart2(input).also { println("part 2 result: $it") }
}

fun findAllSymbols(lines: List<String>): List<CoordinateSymbol> {
    val potentialSymbols = lines.flatMap { line ->
        line.filter { !(it.isDigit() || it == '.') }
            .split("")
    }
    return lines.flatMapIndexed { yIndex, line ->
        line.mapIndexed { xIndex, character -> CoordinateSymbol(xIndex, yIndex, character) }
            .filter { potentialSymbols.contains(it.symbol.toString()) }
    }
}

data class CoordinateSymbol(val xIndex: Int, val yIndex: Int, val symbol: Char)

data class Number(val number: Int, val xIndexRange: IntRange, val yIndex: Int)

val regex = "\\d+".toRegex()

fun parseNumbers(line: String, yIndex: Int): List<Number> {
    fun MatchResult.toNumber(): Number = Number(value.toInt(), range, yIndex)
    return regex.findAll(line)
        .map(MatchResult::toNumber)
        .toList()
}

