package com.beadart.engine

import com.beadart.model.BeadGrid

data class BeadStats(
    val totalBeads: Int,
    val colorCounts: Map<Int, Int>,
    val coveragePercent: Double,
    val usedColors: Int,
    val gridRows: Int,
    val gridCols: Int
)

class BeadEngine {
    fun computeStats(grid: BeadGrid): BeadStats {
        val counts = mutableMapOf<Int, Int>()
        var total = 0
        for (r in 0 until grid.rows) {
            for (c in 0 until grid.cols) {
                val color = grid[r, c]
                if (color != null) {
                    counts[color] = (counts[color] ?: 0) + 1
                    total++
                }
            }
        }
        val totalCells = grid.rows * grid.cols
        return BeadStats(
            totalBeads = total,
            colorCounts = counts,
            coveragePercent = if (totalCells > 0) total.toDouble() / totalCells * 100.0 else 0.0,
            usedColors = counts.size,
            gridRows = grid.rows,
            gridCols = grid.cols
        )
    }

    fun floodFillCount(grid: BeadGrid, startRow: Int, startCol: Int): Int {
        val target = grid[startRow, startCol] ?: return 0
        val visited = Array(grid.rows) { BooleanArray(grid.cols) }
        var count = 0
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(startRow to startCol)
        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeFirst()
            if (r !in 0 until grid.rows || c !in 0 until grid.cols) continue
            if (visited[r][c]) continue
            if (grid[r, c] != target) continue
            visited[r][c] = true
            count++
            queue.addAll(listOf(r-1 to c, r+1 to c, r to c-1, r to c+1))
        }
        return count
    }

    fun validateGrid(rows: Int, cols: Int): Boolean {
        return rows in 1..100 && cols in 1..100
    }

    fun estimateBeadsNeeded(grid: BeadGrid): Int {
        var empty = 0
        for (r in 0 until grid.rows) {
            for (c in 0 until grid.cols) {
                if (grid[r, c] == null) empty++
            }
        }
        return empty
    }
}
