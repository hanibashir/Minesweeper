import Constants.COL_SIZE
import Constants.MINE
import Constants.MINES_NUM
import Constants.ROW_SIZE
import kotlin.random.Random

class MinesField {

    val fieldArray = Array(ROW_SIZE) { Array(COL_SIZE) { '.' } }

    fun drawField(
        tempFieldArray: Array<Array<Char>>,
        rowSize: Int = ROW_SIZE,
        colSize: Int = COL_SIZE,
        minesNum: Int = MINES_NUM
    ) {

        for (i in 0..tempFieldArray[0].lastIndex) {
            if (i == 0) print(" │")
            print("${i + 1}")
            if (i == tempFieldArray[0].lastIndex) println("│")
        }

        println("-│—————————│")
        for (row in tempFieldArray.indices) {
            print("${row + 1}│")
            for (col in tempFieldArray.indices) {
                print(tempFieldArray[row][col])
            }
            print("│")
            println()
        }
        println("-│—————————│")
    }

    fun locateMinesOnField(n: Int) {
        var minesNum = n
        while (minesNum > 0) {
            val rowIndex = Random.nextInt(0, ROW_SIZE) // random int for the row index
            val colIndex = Random.nextInt(0, COL_SIZE) // random int for the column index

            if (!isMine(rowIndex, colIndex)) {
                fieldArray[rowIndex][colIndex] = MINE
                --minesNum
            }
        }
    }

    fun setNeighborsCountOnCell() {
        for (row in fieldArray.indices) {
            for (col in fieldArray.indices) {
                if (!isMine(row, col)) {
                    //if the method [countNeighbourMines] returned Char != '0'
                    if (countNeighbourMines(row, col) != '0')
                        fieldArray[row][col] = countNeighbourMines(row, col)
                }
            }
        }
    }


    /**
     * [countNeighbourMines]: counts the numbers of mines around the cell.
     */
    fun countNeighbourMines(row: Int, col: Int): Char {
        var count = 0
        /*
        Count all the mines in the 8 Neighbour cells
            N.W   N   N.E
              \   |   /
               \  |  /
            W----Cell----E
                 / | \
               /   |  \
            S.W    S   S.E

        Cell-->Current Cell (row, col)
        N -->  North        (row-1, col)
        S -->  South        (row+1, col)
        E -->  East         (row, col+1)
        W -->  West            (row, col-1)
        N.E--> North-East   (row-1, col+1)
        N.W--> North-West   (row-1, col-1)
        S.E--> South-East   (row+1, col+1)
        S.W--> South-West   (row+1, col-1)
    */
        //----------- 1st Neighbour (North) --------
        if (isValidPosition(row - 1, col)) {
            if (isMine(row - 1, col)) count++
        }
        //----------- 2nd Neighbour (South) ------------
        if (isValidPosition(row + 1, col)) {
            if (isMine(row + 1, col)) count++
        }

        //----------- 3rd Neighbour (East) ------------
        if (isValidPosition(row, col + 1)) {
            if (isMine(row, col + 1)) count++
        }
        //----------- 4th Neighbour (West) ------------
        if (isValidPosition(row, col - 1)) {
            if (isMine(row, col - 1)) count++
        }
        //----------- 5th Neighbour (North-East) ------------
        if (isValidPosition(row - 1, col + 1)) {
            if (isMine(row - 1, col + 1)) count++
        }
        //----------- 6th Neighbour (North-West) ------------
        if (isValidPosition(row - 1, col - 1)) {
            if (isMine(row - 1, col - 1)) count++
        }
        //----------- 7th Neighbour (South-East) ------------
        if (isValidPosition(row + 1, col + 1)) {
            if (isMine(row + 1, col + 1)) count++
        }
        //----------- 8th Neighbour (South-West) ------------
        if (isValidPosition(row + 1, col - 1)) {
            if (isMine(row + 1, col - 1)) count++
        }
        // convert the Int to String then get first() Char from the String
        return count.toString().first()
    }

    /**
     * [isValidPosition]: checks if the position of the cell is valid.
     */
    fun isValidPosition(row: Int, col: Int) = row in 0 until ROW_SIZE && col in 0 until COL_SIZE

    /**
     * [isMine]: returns if the cell contains mine or not.
     */
    fun isMine(row: Int, col: Int) = fieldArray[row][col] == MINE

    /**
     * This method [deepCopy] creates a deep copy of the 2D Array of Chars [fieldArray].
     */
    fun deepCopy(fieldArray: Array<Array<Char>>): Array<Array<Char>> {
        val tempArray = Array(ROW_SIZE) { Array(COL_SIZE) { '.' } } // or fieldArray.size - fieldArray[0].size
        for (row in fieldArray.indices) {
            for (col in fieldArray[row].indices) {
                tempArray[row][col] = fieldArray[row][col]
            }
        }
        return tempArray
    }
}
