package com.beadart.engine

import com.beadart.model.BeadGrid
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BeadEngineTest {

    @Test
    fun `computeStats should return zero counts for empty grid`() {
        val grid = BeadGrid(5, 5)
        val stats = BeadEngine().computeStats(grid)
        assertEquals(0, stats.totalBeads)
        assertEquals(0, stats.usedColors)
        assertEquals(0.0, stats.coveragePercent)
    }

    @Test
    fun `computeStats should count placed beads`() {
        val grid = BeadGrid(10, 10)
        grid.placeBead(0, 0, 1)
        grid.placeBead(1, 1, 2)
        grid.placeBead(2, 2, 1)
        val stats = BeadEngine().computeStats(grid)
        assertEquals(3, stats.totalBeads)
        assertEquals(2, stats.usedColors)
    }

    @Test
    fun `computeStats should calculate coverage percent`() {
        val grid = BeadGrid(10, 10)
        grid.placeBead(0, 0, 1)
        grid.placeBead(0, 1, 1)
        val stats = BeadEngine().computeStats(grid)
        assertEquals(2.0, stats.coveragePercent, 0.01)
    }

    @Test
    fun `floodFillCount should count contiguous same-color beads`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(0, 0, 1)
        grid.placeBead(0, 1, 1)
        grid.placeBead(1, 0, 1)
        grid.placeBead(1, 1, 2) // different color
        val count = BeadEngine().floodFillCount(grid, 0, 0)
        assertEquals(3, count)
    }

    @Test
    fun `floodFillCount should return 0 for empty cell`() {
        val grid = BeadGrid(5, 5)
        val count = BeadEngine().floodFillCount(grid, 0, 0)
        assertEquals(0, count)
    }

    @Test
    fun `validateGrid should reject zero or negative dimensions`() {
        assertFalse(BeadEngine().validateGrid(0, 10))
        assertFalse(BeadEngine().validateGrid(10, 0))
        assertFalse(BeadEngine().validateGrid(-1, 10))
    }

    @Test
    fun `validateGrid should accept reasonable dimensions`() {
        assertTrue(BeadEngine().validateGrid(29, 29))
        assertTrue(BeadEngine().validateGrid(1, 1))
        assertTrue(BeadEngine().validateGrid(100, 100))
    }

    @Test
    fun `estimateBeadsNeeded should return empty cell count`() {
        val grid = BeadGrid(3, 3)
        grid.placeBead(1, 1, 1)
        val estimate = BeadEngine().estimateBeadsNeeded(grid)
        assertEquals(8, estimate)
    }
}
