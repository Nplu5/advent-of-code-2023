package org.skrause.two

import org.skrause.two.Colour.*
import org.skrause.two.Draw.Companion.blueDraw
import org.skrause.two.Draw.Companion.greenDraw
import org.skrause.two.Draw.Companion.redDraw
import org.skrause.two.DrawSet.Companion.drawSetOf
import org.skrause.utils.readInput

fun solvePart1(input: List<String>): Int {
    val drawSetLimit = drawSetOf(
        redDraw(12),
        greenDraw(13),
        blueDraw(14)
    )
    return sumOfBreakingGameIds(input, drawSetLimit)
}

fun sumOfBreakingGameIds(input: List<String>, drawSetLimit: DrawSet) =
    input.parseGames()
        .filter { !it.breaksSet(drawSetLimit) }
        .sumOf { it.id }

private fun Game.breaksSet(limit: DrawSet): Boolean {
    return drawSets.any { it.breakSet(limit) }
}

private fun DrawSet.breakSet(limit: DrawSet): Boolean {
    return draws.any { limit.isPossible(it) }
}

private fun DrawSet.isPossible(draw: Draw) = getMatchingDraw(draw.colour).amount < draw.amount

private fun DrawSet.getMatchingDraw(colour: Colour): Draw {
    return when (colour) {
        RED -> draws.firstOrNull { it.colour == RED } ?: redDraw()
        BLUE -> draws.firstOrNull { it.colour == BLUE } ?: blueDraw()
        GREEN -> draws.firstOrNull { it.colour == GREEN } ?: greenDraw()
    }
}

fun solvePart2(input: List<String>): Int {
    return input.parseGames()
        .map(::minimumSet)
        .sumOf { it.power() }
}

fun minimumSet(game: Game): DrawSet {
    val redMaxDraw = maximumDraw(game, RED)
    val blueMaxDraw = maximumDraw(game, BLUE)
    val greenMaxDraw = maximumDraw(game, GREEN)
    return drawSetOf(redMaxDraw, blueMaxDraw, greenMaxDraw)
}

fun DrawSet.power(): Int = draws.map { it.amount }
    .reduce { acc, i -> acc * i }

private fun maximumDraw(game: Game, colour: Colour) = game.drawSets
    .mapNotNull { set -> set.draws.firstOrNull { it.colour == colour } }
    .maxBy { it.amount }

fun main() {
    val input = readInput("Day02/puzzle")
    solvePart1(input).also { println("Part 1 result: $it") }
    solvePart2(input).also { println("part 2 result: $it") }
}

fun List<String>.parseGames(): List<Game> {
    return map(Game::parse)
}

data class Game(val id: Int, val drawSets: List<DrawSet>) {
    companion object {
        private const val GAME_DELIMITER = ": "
        private const val DRAW_SET_DELIMITER = "; "

        fun parse(textToParse: String): Game {
            val (game, drawSetsText) = textToParse.split(GAME_DELIMITER)
            val (_, gameId) = game.split(" ")
            val drawSets = drawSetsText.split(DRAW_SET_DELIMITER)
                .map(DrawSet::parse)
            return Game(gameId.toInt(), drawSets)
        }
    }
}

data class DrawSet(val draws: List<Draw>) {

    companion object {
        private const val DELIMITER = ", "
        fun parse(textToParse: String): DrawSet {
            val draws = textToParse.split(DELIMITER)
                .map(Draw::parse)
            return DrawSet(draws)
        }

        fun drawSetOf(vararg draws: Draw): DrawSet = DrawSet(draws.toList())
    }
}

enum class Colour { RED, BLUE, GREEN }

data class Draw(val amount: Int = 0, val colour: Colour) {
    companion object {
        private const val DELIMITER = " "

        fun parse(textToParse: String): Draw {
            val (amount, colour) = textToParse.split(DELIMITER)
            val parsedAmount = amount.toInt()
            return when (colour) {
                "red" -> redDraw(parsedAmount)
                "blue" -> blueDraw(parsedAmount)
                "green" -> greenDraw(parsedAmount)
                else -> error("Unknown colour: $colour")
            }
        }

        fun redDraw(amount: Int = 0): Draw = Draw(amount, RED)
        fun blueDraw(amount: Int = 0): Draw = Draw(amount, BLUE)
        fun greenDraw(amount: Int = 0): Draw = Draw(amount, GREEN)
    }
}

