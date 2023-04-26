import kotlin.random.Random

class MineField() {
    var land: MutableList<MutableList<Char>> = mutableListOf()

    constructor(
        height: Int,
        width: Int,
        mine: Int
    ) : this() {
        land = MutableList(height) {
            MutableList(width) { '.' }
        }
        repeat(mine) {
            setMine(height, width)
        }

    }

    private fun setMine(height: Int, width: Int) {
        val x = Random.nextInt(0, height)
        val y = Random.nextInt(0, width)
        if (land[x][y] == '.') {
            land[x][y] = 'X'
        } else setMine(height, width)
    }

    fun output() {
        for (i in 0..land[0].lastIndex) {
            println(land[i].joinToString(""))
        }
    }
}

fun main() {
    println("How many mines do you want on the field?")
    val mines = readln().toInt()
    var mineField = MineField(9, 9, mines)
    mineField.output()
}