// BeadGrid.kt — bead-art editor model (undo/redo, flood fill, serialize)
package com.beadart.model

data class Pixel(val row: Int, val col: Int)

class GridAction(val row: Int, val col: Int, val prevColor: Int?, val newColor: Int?)

class BeadGrid(var rows: Int = 29, var cols: Int = 29) {
    private var cells: Array<Array<Int?>> = Array(rows) { arrayOfNulls(cols) }
    private val undoStack = mutableListOf<GridAction>()
    private val redoStack = mutableListOf<GridAction>()

    private var cachedBeadCount: Int = 0
    private var beadCountDirty: Boolean = true

    val canUndo: Boolean get() = undoStack.isNotEmpty()
    val canRedo: Boolean get() = redoStack.isNotEmpty()

    val beadCount: Int get() {
        if (!beadCountDirty) return cachedBeadCount
        var count = 0
        for (row in cells) for (cell in row) if (cell != null) count++
        cachedBeadCount = count; beadCountDirty = false
        return count
    }

    operator fun get(r: Int, c: Int): Int? = if (r in 0 until rows && c in 0 until cols) cells[r][c] else null

    fun placeBead(r: Int, c: Int, colorIndex: Int) {
        if (r !in 0 until rows || c !in 0 until cols) return
        val prev = cells[r][c]; if (prev == colorIndex) return
        undoStack += GridAction(r, c, prev, colorIndex)
        redoStack.clear()
        cells[r][c] = colorIndex
        beadCountDirty = true
    }

    fun eraseBead(r: Int, c: Int) {
        if (r !in 0 until rows || c !in 0 until cols) return
        val prev = cells[r][c] ?: return
        undoStack += GridAction(r, c, prev, null)
        redoStack.clear()
        cells[r][c] = null
        beadCountDirty = true
    }

    fun fillAt(r: Int, c: Int, colorIndex: Int) {
        if (r !in 0 until rows || c !in 0 until cols) return
        val target = cells[r][c]; if (target == colorIndex) return
        val toFill = mutableListOf<Pair<Int, Int>>()
        val visited = Array(rows) { BooleanArray(cols) }
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue += r to c
        while (queue.isNotEmpty()) {
            val (cr, cc) = queue.removeFirst()
            if (cr !in 0 until rows || cc !in 0 until cols || visited[cr][cc]) continue
            if (cells[cr][cc] != target) continue
            visited[cr][cc] = true; toFill += cr to cc
            queue += listOf(cr - 1 to cc, cr + 1 to cc, cr to cc - 1, cr to cc + 1)
        }
        if (toFill.isEmpty()) return
        for ((fr, fc) in toFill) { undoStack += GridAction(fr, fc, cells[fr][fc], colorIndex); cells[fr][fc] = colorIndex }
        redoStack.clear(); beadCountDirty = true
    }

    fun undo() {
        if (undoStack.isEmpty()) return
        val action = undoStack.removeLast()
        redoStack += GridAction(action.row, action.col, cells[action.row][action.col], action.prevColor)
        cells[action.row][action.col] = action.prevColor
        beadCountDirty = true
    }

    fun redo() {
        if (redoStack.isEmpty()) return
        val action = redoStack.removeLast()
        undoStack += GridAction(action.row, action.col, cells[action.row][action.col], action.newColor)
        cells[action.row][action.col] = action.newColor
        beadCountDirty = true
    }

    fun clear() {
        val snapshot = cells
        cells = Array(rows) { arrayOfNulls(cols) }
        for (r in 0 until rows) for (c in 0 until cols) {
            if (snapshot[r][c] != null) undoStack += GridAction(r, c, snapshot[r][c], null)
        }
        redoStack.clear(); beadCountDirty = true
    }
}
