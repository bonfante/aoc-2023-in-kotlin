fun main() {
    data class Walker(
        val map: Map<String, Pair<String, String>>,
        val firstStep: String = map.keys.first(),
    ) {

        fun walk(from: String, instructions: Sequence<String>, to: (String) -> Boolean): Pair<String, Long> {
            var from = from;
            var steps = 0.toLong()
            for (instruction in instructions) {
                if (to(from)) {
                    break
                }
                from = if (instruction == "L") {
                    map[from]!!.first
                } else {
                    map[from]!!.second
                }
                steps += 1
            }
            return Pair(from, steps)
        }

    }

    fun part1(input: List<String>): Long {
        val input = input.toMutableList()
        val instructionsGroups = input.removeFirst().chunked(1).asSequence().repeat()
        input.removeFirst()
        val walker = Walker(input.associate {
            val (_, current, left, right) = "(\\w+)\\s*=\\s*\\((\\w+),\\s(\\w+)\\)".toRegex()
                .matchEntire(it)!!.groupValues;
            Pair(current, Pair(left, right))
        })

        val to = "ZZZ"
        var from = "AAA"
        var steps = 0.toLong()
        for (instruction in instructionsGroups.chunked(1)) {
            val result = walker.walk(from, instruction.asSequence()) { it == to }
            from = result.first
            steps += result.second
            if (from == to) {
                break
            }
        }
        return steps
    }


    fun part2(input: List<String>): Long {
        val input = input.toMutableList()
        val instructionsGroups = input.removeFirst().chunked(1).asSequence().repeat()
        input.removeFirst()

        val walker = Walker(input.associate {
            val (_, current, left, right) = "(\\w+)\\s*=\\s*\\((\\w+),\\s(\\w+)\\)".toRegex()
                .matchEntire(it)!!.groupValues;
            Pair(current, Pair(left, right))
        })

        // from Advent of Code 2023 in Kotlin Day 8 on YT, way better than brute forcing it
        return walker.map.keys.filter { it.endsWith("A") }
            .map { walker.walk(it, instructionsGroups.asSequence()) { it.endsWith("Z") }.second.toBigInteger() }
            .reduce { acc, steps -> acc * steps / acc.gcd(steps) }
            .toLong()
    }

    check(part1(readInput("day08inputTest1")) == 2.toLong())
    check(part1(readInput("day08inputTest2")) == 6.toLong())
    println(part1(readInput("day08input")))
    check(part2(readInput("day08inputTest3")) == 6.toLong())
    println(part2(readInput("day08input")))
}

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }
