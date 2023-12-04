fun main() {
    val mapDigits = { match: String ->
        when (match) {
            "one", "1" -> "1"
            "two", "2" -> "2"
            "three", "3" -> "3"
            "four", "4" -> "4"
            "five", "5" -> "5"
            "six", "6" -> "6"
            "seven", "7" -> "7"
            "eight", "8" -> "8"
            "nine", "9" -> "9"
            else -> {
                throw Exception("")
            }
        }
    }

    fun part1(input: List<String>): Int {
        var acc = 0;
        input.forEach() {
            var first: Char? = null
            var last: Char? = null
            for (ch in it.iterator()) {
                if (!ch.isDigit()) {
                    continue
                }
                if (first == null) {
                    first = ch
                }
                last = ch
            }
            if (first == null || last == null) {
                throw Exception("")
            }
            acc += "$first$last".toInt()
        }
        return acc
    }

    fun part2(input: List<String>): Int {
        var acc = 0;
        var pattern = "(?=(one|two|three|four|five|six|seven|eight|nine|\\d))".toRegex() ?: throw Exception("")
        input.forEach() {
            val matchResult = pattern.findAll(it)
            val firstMatch = matchResult.first().groupValues[1]
            val lastMatch = matchResult.last().groupValues[1]
            acc += "${mapDigits(firstMatch)}${mapDigits(lastMatch)}".toInt()
        }
        return acc
    }

    fun part2NoRegex(input: List<String>): Int {
        var acc = 0;
        input.forEach() {
            val numbersList = listOf(
                "one", "1",
                "two", "2",
                "three", "3",
                "four", "4",
                "five", "5",
                "six", "6",
                "seven", "7",
                "eight", "8",
                "nine", "9",
            )
            val first = it.findAnyOf(numbersList)?.second ?: throw Exception()
            val last = it.findLastAnyOf(numbersList)?.second ?: first
            acc += "${mapDigits(first)}${mapDigits(last)}".toInt()
        }
        return acc
    }

    check(part1(readInput("day01input")) == 54630)
    check(part2(readInput("day01input")) == 54770)
    check(part2NoRegex(readInput("day01input")) == 54770)
}
