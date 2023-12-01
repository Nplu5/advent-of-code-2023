fun main() {
    fun part1(input: List<String>): Int {
        return calibrate(input)
    }

    fun part2(input: List<String>): Int {
        return calibrate(replaceSpelledOutDigits(input))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testResult = part1(testInput).also { println(it) }
    check(testResult == 142)

    val testInput2 = readInput("Day01_test02")
    val testResult2 = part2(testInput2).also { println(it) }
    check(testResult2 == 281)

    val input = readInput("Day01")
    val resultPart1 = part1(input).also { println(it) }
    val resultPart2 = part2(input).also { println(it) }

}

val DIGITS  = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

fun replaceSpelledOutDigits(scrambledCoordinates: List<String>): List<String> {
    return scrambledCoordinates.map { scrambledLine ->
        val firstDigit = DIGITS.map {it to  scrambledLine.indexOf(it.key) }.minBy { it.second }.first
        // Potential problem lowest might overwrite highest no there as there would be other digits surrounding in this case
        val lastDigit = DIGITS.map {it to  scrambledLine.indexOf(it.key) }.maxBy { it.second }.first

        scrambledLine.replace(firstDigit.key, firstDigit.value)
            .replace(lastDigit.key, lastDigit.value)
            .also { println(it) }
    }
}

/*
Problem: The string representations can occur overlapping, e.g. zoneight234
two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
281
 */
fun calibrate(scrambledCoordinates: List<String>): Int {
    return scrambledCoordinates.map { scrambledLine -> scrambledLine.filter { it.isDigit() } }
        .map { digitLine -> "${digitLine.first()}${digitLine.last()}" }
        .sumOf { it.toInt() }
}
