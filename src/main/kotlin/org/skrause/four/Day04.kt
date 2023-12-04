package org.skrause.four

import org.skrause.utils.readInput
import kotlin.math.pow

fun main() {
    val input = readInput("Day04/puzzle")
    solvePart1(input).also { println("Part 1 result: $it") }
    solvePart2(input).also { println("part 2 result: $it") }
}

fun solvePart2(input: List<String>): Int {
    val cardIdMap = input.asSequence()
        .map { Scratchcard.parse(it) }
        .map { it.toWinningCard() }
        .associateBy { it.id }

    return cardIdMap.map { it.value }
        .fold(0) { acc, card ->
            acc + card.getWinningCardsAmount(cardIdMap)
        }
}

private fun WinningCard.getWinningCardsAmount(cardIdMap: Map<CardId, WinningCard>): Int {
    return additionalIds.mapNotNull {
        cardIdMap[it]?.getWinningCardsAmount(cardIdMap)
    }.sumOf { it } + 1 // plus 1 for the card itself
}

fun Scratchcard.toWinningCard(): WinningCard {
    val winning = matchingNumbers().toMatchingCardsId(id)
    return WinningCard(id, winning, 1)
}

data class WinningCard(val id: CardId, val additionalIds: List<CardId>, val amount: Int)

fun List<Int>.toMatchingCardsId(startingId: CardId): List<CardId> {
    return (1..size).map { startingId + it }.map { it }

}

fun Scratchcard.matchingNumbers(): List<Int> {
    return cardNumbers.filter { winningNumbers.contains(it) }
}

fun solvePart1(input: List<String>): Int {
    return input.asSequence()
        .map { Scratchcard.parse(it) }
        .map { it.matchingNumbers() }
        .filter { it.isNotEmpty() }
        .map { it.calculatePoints() }
        .sumOf { it }
}

private fun List<Int>.calculatePoints(): Int {
    return 2.0.pow((size - 1).toDouble()).toInt()
}

data class Scratchcard(val winningNumbers: List<Int>, val cardNumbers: List<Int>, val id: CardId) {
    companion object {
        private const val DELIMITER = ":"
        private const val NUMBERS_DELIMITER = "|"
        fun parse(textToParse: String): Scratchcard {
            val (cards, numbers) = textToParse.split(DELIMITER)
            val id = cards.split(" ")
                .filter { it.isNotEmpty() }[1].toInt()
            val (winningNumbersText, drawnNumbersText) = numbers.split(NUMBERS_DELIMITER)

            return Scratchcard(
                winningNumbersText.extractNumbers(),
                drawnNumbersText.extractNumbers(),
                id
            )
        }
    }
}

typealias CardId = Int

fun String.extractNumbers(): List<Int> = split(" ")
    .filter { it.isNotEmpty() }
    .map { it.toInt() }