import kotlin.random.Random

class Array2D() {
    private var x = mutableListOf<Int>()
    private var y = mutableListOf<Int>()

    fun add(inputX: Int, inputY: Int) {
        x.add(inputX)
        y.add(inputY)
    }

    fun delete(index: Int) {
        var lastInd: Int = x.lastIndex
        //moving last items to the place we need to clear
        if (lastInd > 0) {
            x[index] = x[lastInd]
            y[index] = y[lastInd]

            //delete last items
            x.removeAt(lastInd)
            y.removeAt(lastInd)
        } else {
            x.removeAt(0)
            y.removeAt(0)
        }
    }

    fun isEmpty(): Boolean {
        return x.isEmpty()
    }

    fun getLastIndex() = x.lastIndex

    fun getIndex(retX: Int, retY: Int): Int {
        for (i in 0..x.lastIndex) {
            if (x[i] == retX && y[i] == retY) {
                return i
            }
        }
        return -1
    }

}



class MineField() {
    var land: MutableList<MutableList<Char>> = mutableListOf()
    var minesList: Array2D = Array2D()
    var notMinesList: Array2D = Array2D()

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
        val x = Random.nextInt(height)
        val y = Random.nextInt(width)
        if (land[x][y] != 'X') {
            land[x][y] = 'X'
            setNums(x, y)
        } else setMine(height, width)

    }

    fun setNums(x: Int, y: Int) {
        for (i in x - 1..x + 1) {
            if (i >= 0 && i < 9) {
                for (j in y - 1..y + 1) {
                    if (j >= 0 && j < 9) {
                        if (land[i][j] == '.') {
                            land[i][j] = '1'
                        } else if (land[i][j].isDigit()) {
                            land[i][j] = land[i][j].plus(1)
                        }
                    }
                }
            }
        }
    }

    fun setValue(x: Int, y: Int, value: Char) {
        land[x][y] = value
    }

    fun checkProgress(mine: Int): Boolean {
        return notMinesList.isEmpty() && minesList.getLastIndex() == mine - 1
    }

    fun secretOutput() {
        println(" |123456789|")
        println("-|---------|")
        for (i in 0..land[0].lastIndex) {
            print("${i + 1}|")
            for (j in 0..land[0].lastIndex) {
                if (land[i][j] == 'X') {
                    print(".")
                } else print(land[i][j])
            }
            println("|")
        }
        println("-|---------|")
    }
}

fun main() {
    println("How many mines do you want on the field?")
    val mines = readln().toInt()
    var mineField = MineField(9, 9, mines)
    mineField.secretOutput()

    while (!mineField.checkProgress(mines)) {
        println("Set/delete mines marks (x and y coordinates):")
        var (inp2, inp1) = readln().split(" ")
        var x = inp1.toInt() - 1
        var y = inp2.toInt() - 1

        when (mineField.land[x][y]) {
            //put * on X's place, X in memory
            'X' -> {
                mineField.minesList.add(x, y)
                mineField.setValue(x, y, '*')
                println("shit")
            }
            //put * on a safe place, put safe place coordinates to memory list
            '.' -> {
                mineField.notMinesList.add(x, y)
                mineField.setValue(x, y, '*')
                println("cum")
            }
            //removing *:
            '*' -> {
                //a) There was X. Delete X coordinates from memory list, put it back
                if (mineField.minesList.getIndex(x, y) != -1) {
                    mineField.minesList.delete(mineField.minesList.getIndex(x, y))
                    mineField.setValue(x, y, 'X')
                    println("horny")
                }
                //b) There was '.'. Delete '.' coordinates from memory, put it back
                if (mineField.notMinesList.getIndex(x, y) != -1) {
                    mineField.notMinesList.delete(mineField.notMinesList.getIndex(x, y))
                    mineField.setValue(x, y, '.')
                    println("jail")
                }
            }
            //number exception
            else -> println("There is a number here!")
        }
        mineField.secretOutput()
    }
    println("Congratulations! You found all the mines!")
}


