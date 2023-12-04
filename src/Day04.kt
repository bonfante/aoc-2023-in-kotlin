fun main() {

    fun part1(input: List<String>): Int {
        return input.map {
            val (winningNumbers, scratchcardNumbers) = it.split(':')[1].split('|')
                .map {
                    it.split(' ')
                        .map { it.trim() }
                        .filter { !it.isEmpty() }
                }
            val n = winningNumbers.intersect(scratchcardNumbers).size

            if (n == 0) {
                0
            } else {
                Math.pow(2.0, (n - 1).toDouble()).toInt()
            }
        }.sum()
    }


    fun part2(input: List<String>): Int {
        val scratchcards: MutableMap<Int, Int> = mutableMapOf()
        input.forEach() {
            val scratchData = it.split(":");
            val gameId = scratchData.first().removePrefix("Card").trim().toInt()
            scratchcards[gameId] = scratchcards.getOrElse(gameId) { 0 } + 1;
            val (winningNumbers, scratchcardNumbers) = scratchData[1].split('|').map {
                it.split(' ')
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
            }
            val n = winningNumbers.intersect(scratchcardNumbers).size
            (gameId + 1).rangeTo(n + gameId).forEach {
                val numberOfCopies = scratchcards.getOrElse(gameId) { 1 }
                val numberOfCards = scratchcards.getOrElse(it) { 0 }
                scratchcards[it] = numberOfCards + numberOfCopies
            }
        }
        return scratchcards.values.sum()
    }


    check(part1(readInput("day04inputTest")) == 13)
    check(part1(readInput("day04input")) == 21919)
    check(part2(readInput("day04inputTest")) == 30)
    check(part2(readInput("day04input")) == 9881048)
}
