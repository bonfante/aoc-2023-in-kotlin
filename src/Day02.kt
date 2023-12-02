import java.io.File
import kotlin.math.max
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            var (game, dices) = it.removePrefix("Game ").split(':', limit = 2)
            var gameSets = dices.split(';')
                    .map {
                        it.split(',')
                                .map {
                                    it.trim().split(' ')
                                            .map { it.trim() }
                                }
                    }

            val impossible = gameSets.any {
                it.any {
                    when (it[1]) {
                        "red" -> it[0].toInt() > 12
                        "green" -> it[0].toInt() > 13
                        "blue" -> it[0].toInt() > 14
                        else -> throw Exception("No color ${it[1]}")
                    }
                }
            }
            if (impossible) {
                0
            } else {
                game.toInt()
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map {
            var (game, dices) = it.removePrefix("Game ").split(':', limit = 2)
            var gameSets = dices.split(';')
                    .map {
                        it.split(',')
                                .map {
                                    it.trim().split(' ')
                                            .map { it.trim() }
                                }
                    }

            var green = 0;
            var blue = 0;
            var red = 0;
            for (gameSet in gameSets) {
                for (dices in gameSet) {
                    when (dices[1]) {
                        "red" -> red = max(red, dices[0].toInt())
                        "green" -> green = max(green, dices[0].toInt())
                        "blue" -> blue = max(blue, dices[0].toInt())
                    }
                }
            }
            green*blue*red
        }.sum()
    }

    check(part1(readInput("day02input")) == 2278)
    println(part2(readInput("day02input")))
    check(part2(readInput("day02input")) == 67953)
//    check(part2(readInput("day01input")) == 54770)
}
