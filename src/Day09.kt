fun main() {
    fun extrapolateSensorData(input: List<String>): List<List<List<Long>>> {
        return input
            .map { it.split(' ').map { it.toLong() } }
            .map {
                val diffUntilZero: MutableList<List<Long>> = mutableListOf()
                var current = it
                diffUntilZero.add(current)
                while (!current.all { it == 0.toLong() }) {
                    current = current.windowed(2).map { it[1] - it[0] }
                    diffUntilZero.add(current)
                }

                diffUntilZero.toList()
            }
    }


    fun part1(input: List<String>): Long {
        return extrapolateSensorData(input).sumOf {
            it.map { it.last() }
                .reversed()
                .reduce { acc: Long, l: Long -> acc - l }
                .unaryMinus()
        }
    }


    fun part2(input: List<String>): Long {
        return extrapolateSensorData(input).sumOf {
            it.map { it.first() }
                .reversed()
                .reduce { acc: Long, l: Long -> l - acc }
        }
    }

    check(part1(readInput("day09inputTest")) == 114.toLong())
    println(part1(readInput("day09input")))
    check(part2(readInput("day09inputTest")) == 2.toLong())
    println(part2(readInput("day09input")))
}
