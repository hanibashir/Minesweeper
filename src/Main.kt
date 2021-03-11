import Constants.COL_SIZE
import Constants.FREE
import Constants.MINE
import Constants.ROW_SIZE
import UserInput.getCoordinates
import UserInput.minesUserInput
import java.lang.Character.isDigit

fun main() {
    var gameOver = false
    var foundedMines = 0
    val minesInput = minesUserInput()
    val field = MinesField()
    //locate the mines on the field
    field.locateMinesOnField(minesInput)
    // calculate neighbors
    field.setNeighborsCountOnCell()
    // create deep copy of the field array
    val tempFieldArray = Array(ROW_SIZE) { Array(COL_SIZE) { '.' } }
    field.drawField(tempFieldArray = tempFieldArray, minesNum = minesInput)

    var msg = ""

    while (!gameOver) {
        // get coordinates from user input
        val (col, row, cellState) = getCoordinates(field.fieldArray, tempFieldArray)
        when (cellState) {
            "free" -> {
                if (field.isMine(row - 1, col - 1)) {
                    tempFieldArray[row - 1][col - 1] = MINE
                    msg = "You stepped on a mine and failed!"
                    gameOver = true
                } else {
                    if (field.countNeighbourMines(row, col) != '0') {
                        tempFieldArray[row - 1][col - 1] = field.countNeighbourMines(row, col)
                    } else {
                        tempFieldArray[row - 1][col - 1] = FREE
                    }

                    for (row in field.fieldArray.indices) {
                        for (col in field.fieldArray[row].indices) {
                            if (isDigit(field.fieldArray[row][col])) {
                                tempFieldArray[row][col] = field.countNeighbourMines(row, col)
                                continue
                            } else if (field.fieldArray[row][col] == MINE) {
                                continue
                            }
                            tempFieldArray[row][col] = FREE
                        }
                    }

                    for (row in field.fieldArray.lastIndex downTo 0) {
                        for (col in field.fieldArray.lastIndex downTo 0) {
                            if (isDigit(field.fieldArray[row][col])) {
                                tempFieldArray[row][col] = field.countNeighbourMines(row, col)
                                continue
                            } else if (field.fieldArray[row][col] == MINE) {
                                continue
                            }
                            tempFieldArray[row][col] = FREE
                        }
                    }
                }
            }
            "mine" -> {
                if (tempFieldArray[row - 1][col - 1] == CellState.MARKED.state) {
                    tempFieldArray[row - 1][col - 1] = CellState.UNMARKED.state
                } else {
                    tempFieldArray[row - 1][col - 1] = CellState.MARKED.state
                }
                if (field.isMine(row - 1, col - 1)) {

                    if (tempFieldArray[row - 1][col - 1] == CellState.MARKED.state) {
                        if (field.isMine(row - 1, col - 1)) foundedMines++
                        if (foundedMines == minesInput) {
                            msg = "Congratulations! You found all the mines!"
                            gameOver = true
                        }
                    }
                }
            }
        }

        field.drawField(tempFieldArray = tempFieldArray, minesNum = minesInput)
    }
    if (gameOver) println(msg)
}
