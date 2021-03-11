import Constants.COL_SIZE
import Constants.ROW_SIZE

object UserInput {
    fun minesUserInput(): Int {
        var minesNum = 0
        // field size
        val fieldSize = ROW_SIZE * COL_SIZE
        while (minesNum <= 0 || minesNum > fieldSize) {
            print("How many mines do you want on the field? ")
            minesNum = readLine()!!.toIntOrNull()!!
            if (minesNum <= 0) println("Only non-negative numbers allowed\n")
            // if the entered mines number != greater than the field size
            if (minesNum > fieldSize) println("Too much mines. Field size is ($fieldSize)\n")
        }

        return minesNum
    }

    fun getCoordinates(fieldArray: Array<Array<Char>>, tempFieldArray: Array<Array<Char>>): Triple<Int, Int, String> {

        print("Set/unset mine marks or claim a cell as free: ")
        val input = readLine()!!.split(" ")
        val colNum = input[0].toInt()
        val rowNum = input[1].toInt()
        val cellState = input[2]

        return when {
            colNum !in 1..9 || rowNum !in 1..9 -> {
                println("Wrong Coordinates. Please enter numbers between 1-9 e.g. 1 1 free OR 1 1 mine")
                getCoordinates(fieldArray, tempFieldArray)
            }
            cellState != "free" && cellState != "mine" -> {
                println("After Coordinates. Please enter the state of the cell. e.g. 1 1 free OR 1 1 mine")
                getCoordinates(fieldArray, tempFieldArray)
            }
            else -> Triple(colNum, rowNum, cellState)
        }
    }
}