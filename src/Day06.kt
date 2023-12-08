import kotlin.math.max

fun main() {

    data class RaceRecord(val time: Long, val distance: Long)
    data class RaceAttempt(val record: RaceRecord, val holdingBtnTime: Long) {
        fun distance(): Long {
            val holdingTime = record.time - holdingBtnTime
            return holdingTime * (record.time - holdingTime)
        }

        fun newRecord(): Boolean {
            return distance() > record.distance
        }
    }

    fun findLowestTimeHoldingToWin(input: RaceAttempt): Long {
        var attempt = input;
        while (attempt.newRecord()) {
            attempt = attempt.copy(holdingBtnTime = max(0, attempt.holdingBtnTime / 2))
        }
        while (!attempt.newRecord()) {
            attempt = attempt.copy(holdingBtnTime = attempt.holdingBtnTime + 1)
        }
        return attempt.holdingBtnTime
    }

    fun part1(input: List<String>): Long {
        val timings = input.first().split(':').last().trim().split("\\s+".toRegex()).map { it.toLong() }
        val distances =
            input.last().split(':').last().trim().split("\\s+".toRegex()).map { it.toLong() }.toMutableList()

        return timings.map {
            val halfRecord = it / 2
            val halfRemain = it % 2
            val firstAttempt = RaceAttempt(RaceRecord(it, distances.removeFirst()), halfRecord);
            val lowestTimeHoldingBtn = findLowestTimeHoldingToWin(firstAttempt)
            val highestTimeHoldingBtn = halfRecord + (halfRecord - lowestTimeHoldingBtn) + halfRemain
            highestTimeHoldingBtn - lowestTimeHoldingBtn + 1
        }.reduce { acc, i -> acc * i }
    }


    fun part2(input: List<String>): Long {
        val timing = input.first().split(':').last().replace(" ", "").toLong()
        val distance = input.last().split(':').last().replace(" ", "").toLong()


        val halfRecord = timing / 2
        val halfRemain = timing % 2
        val firstAttempt = RaceAttempt(RaceRecord(timing, distance), halfRecord);
        val lowestTimeHoldingBtn = findLowestTimeHoldingToWin(firstAttempt)
        val highestTimeHoldingBtn = halfRecord + (halfRecord - lowestTimeHoldingBtn) + halfRemain
        return highestTimeHoldingBtn - lowestTimeHoldingBtn + 1
    }

    check(part1(readInput("day06inputTest")) == 288.toLong())
    check(part1(readInput("day06input")) == 4811940.toLong())
    check(part2(readInput("day06inputTest")) == 71503.toLong())
    check(part2(readInput("day06input")) == 30077773.toLong())
}
