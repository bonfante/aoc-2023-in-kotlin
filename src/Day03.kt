import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

fun main() {
    fun extractNumber(input: String, pos: Int): List<Int> {
        var startNumber: Int = pos;
        for (i1 in (-1).rangeTo(pos).reversed()) {
            if (i1 >= 0 && input[i1].isDigit()) {
                continue
            } else {
                startNumber = i1 + 1;
                break
            }
        }
        var endNumber: Int = pos;
        for (i1 in pos.rangeTo(input.length)) {
            if (i1 < input.length && input[i1].isDigit()) {
                continue
            } else {
                endNumber = i1;
                break
            }
        }
        return listOf(input.substring(startNumber, endNumber).toInt(), startNumber, endNumber - 1)
    }

    fun part1(input: List<String>): Int {
        var input = input.toMutableList()
        input.add("")
        input.add(0, "")
        var sum = 0;
        input.windowed(3).forEach {
            val (top, mid, low) = it;
            var i = 0;
            while (i < mid.length) {
                val midChar = mid.getOrNull(i) ?: break;
                if (!midChar.isDigit()) {
                    i++
                    continue
                }
                val symbolFound = mid.hasSymbolAt(i - 1)
                        || mid.hasSymbolAt(i + 1)
                        || top.hasSymbolAt(i - 1)
                        || top.hasSymbolAt(i)
                        || top.hasSymbolAt(i + 1)
                        || low.hasSymbolAt(i - 1)
                        || low.hasSymbolAt(i)
                        || low.hasSymbolAt(i + 1)
                if (!symbolFound) {
                    i++
                    continue
                }

                val (number, _, end) = extractNumber(mid, i)
                sum += number
                i = end + 1
            }
        }
        return sum
    }


    fun part2(input: List<String>): Int {
        var input = input.toMutableList()
        input.add(0, "")
        var sum = 0;
        input.windowed(3).forEach {
            val (top, mid, low) = it;
            var i = 0;
            while (i < mid.length) {
                if (!mid.hasStarAt(i)) {
                    i++
                    continue
                }
                var listOfNumber: MutableList<Int> = listOf<Int>().toMutableList()


                if (mid.hasDigitAt(i - 1)) {
                    var (n, _, _) = extractNumber(mid, i - 1);
                    listOfNumber.add(n)
                }
                if (mid.hasDigitAt(i + 1)) {
                    var (n, _, _) = extractNumber(mid, i + 1);
                    listOfNumber.add(n)
                }
                for (l in listOf(top, low)) {
                    for (idx in listOf(i - 1, i, i + 1)) {
                        if (l.hasDigitAt(idx)) {
                            val (n, s, e) = extractNumber(l, idx);
                            listOfNumber.add(n)
                            if (e > idx) {
                                break
                            }
                        }
                    }
                }
                i++
                if (listOfNumber.size != 2) {
                    continue;
                }
                sum += listOfNumber[0] * listOfNumber[1]
            }
        }
        return sum
    }

    check(part1(readInput("day03inputTest")) == 4361)
    check(part1(readInput("day03input")) == 525181)
    check(part2(readInput("day03inputTest")) == 467835)
    check(part2(readInput("day03input")) == 84289137)
}

private fun Char.isSymbol(): Boolean {
    return !isDigit() && !equals('.')
}

private fun String.hasSymbolAt(index: Int): Boolean {
    return (getOrNull(index) ?: '.').isSymbol()
}
private fun String.hasDigitAt(index: Int): Boolean {
    return (getOrNull(index) ?: '.').isDigit()
}
private fun String.hasStarAt(index: Int): Boolean {
    return (getOrNull(index) ?: '.')=='*'
}
