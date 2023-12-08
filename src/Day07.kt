fun main() {

    data class Hand(val hand: String, val bid: Int) : Comparable<Hand> {
        val cards = hand.chunked(1).map { cardToInt(it) }
        val handType: Int = handType()

        private fun handType(): Int {
            val (j, normalCards) = cards.partition { it == 1 }
            val jokers = j.size

            val groups: MutableList<Int> = normalCards.groupBy { it }
                .values
                .filter { it.size > 1 }
                .map { it.size }
                .sorted()
                .toMutableList()

            val result = when (groups) {
                //Five of a kind
                listOf(5) -> 6
                //Four of a kind
                listOf(4) -> 5 + jokers
                //Full house
                listOf(2, 3) -> 4
                //Three of a kind
                listOf(3) -> when (jokers) {
                    0 -> 3
                    else -> 4 + jokers
                }
                //Two pair
                listOf(2, 2) -> 2 + jokers
                //One pair
                listOf(2) -> when (jokers) {
                    2 -> 5
                    1 -> 3
                    else -> 1
                }
                //High card
                emptyList<Int>() -> 0 + jokers
                else -> throw Exception();
            }

            return result
        }

        override fun compareTo(other: Hand): Int {
            val result = if (handType == other.handType) {
                compareHand(other.cards)
            } else {
                handType compareTo other.handType
            }
            return result
        }

        private fun compareHand(otherCards: List<Int>): Int {
            val otherCardsMutable = otherCards.toMutableList()

            var result = 0
            for (card in cards) {
                result = card compareTo otherCardsMutable.removeFirst()
                if (result != 0) {
                    break
                }
            }
            return result
        }

        private fun cardToInt(input: String): Int {
            return when (input) {
                "A" -> 14
                "K" -> 13
                "Q" -> 12
                "T" -> 10
                "J" -> 1
                else -> input.toInt()
            }
        }
    }


    fun buildHand(input: String): Hand {
        val (hand, bid) = input.split(' ')

        return Hand(hand, bid.toInt())
    }


    fun part1(input: List<String>): Int {
        return input.map { buildHand(it) }
            .sorted()
            .mapIndexed { index: Int, it: Hand -> (index + 1) * it.bid }
            .sum()
    }


    fun part2(input: List<String>): Long {
        return 1
    }

//    check(part1(readInput("day07inputTest")) == 6440)
//    println(part1(readInput("day07input")))
    check(part1(readInput("day07inputTest")) == 5905)
    println(part1(readInput("day07input")))
}
