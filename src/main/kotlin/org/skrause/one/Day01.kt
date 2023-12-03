package org.skrause.one

import org.skrause.utils.readInput

fun solvePart1(input: List<String>): Int {
    return input.extractCalibrationCoordinates()
}

fun solvePart2(input: List<String>): Int {
    return input.replaceSpelledOutDigits()
        .extractCalibrationCoordinates()
}

fun main() {
    val input = readInput("Day01/puzzle")
    solvePart1(input).also { println("Part 1 result: $it") }
    solvePart2(input).also { println("part 2 result: $it") }
}

fun List<String>.extractCalibrationCoordinates(): Int {
    return map { scrambledLine -> scrambledLine.filter { it.isDigit() } }
        .map { digitLine -> "${digitLine.first()}${digitLine.last()}" }
        .sumOf { it.toInt() }
}

fun List<String>.replaceSpelledOutDigits(): List<String> {
    return map { scrambledLine ->
        replaceSpelledOutDigitsSolution(scrambledLine)
    }
}

private val DIGIT_WORDS = listOf(
    Replacement("one", "1one", -1),
    Replacement("two", "2two", -1),
    Replacement("three", "3three", -1),
    Replacement("four", "4four", -1),
    Replacement("five", "5five", -1),
    Replacement("six", "6six", -1),
    Replacement("seven", "7seven", -1),
    Replacement("eight", "8eight", -1),
    Replacement("nine", "9nine", -1)
)

private fun replaceSpelledOutDigitsSolution(scrambledLine: String): String {
    val firstReplacementIndex = DIGIT_WORDS.map { it.copy(index = scrambledLine.indexOf(it.oldValue)) }
        .filter { it.index != -1 }
        .minByOrNull { it.index }
    val secondReplacementIndex = DIGIT_WORDS.map { it.copy(index = scrambledLine.lastIndexOf(it.oldValue)) }
        .filter { it.index != -1 }
        .maxByOrNull { it.index }

    return scrambledLine.replace(firstReplacementIndex)
        .replace(secondReplacementIndex)
}

private data class Replacement(val oldValue: String, val newValue: String, val index: Int)

private fun String.replace(replacement: Replacement?): String {
    return if (replacement != null) {
        replace(replacement.oldValue, replacement.newValue)
    } else {
        this
    }
}
