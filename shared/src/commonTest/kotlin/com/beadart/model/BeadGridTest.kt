package com.beadart.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BeadGridTest {

    @Test
    fun `default grid should be 29x29 with all cells null`() {
        val grid = BeadGrid()
        assertEquals(29, grid.rows)
        assertEquals(29, grid.cols)
        assertEquals(0, grid.beadCount)
    }

    @Test
    fun `placeBead should set cell and increment count`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(2, 3, 5)
        assertEquals(5, grid[2, 3])
        assertEquals(1, grid.beadCount)
    }

    @Test
    fun `placeBead should not change if same color`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(0, 0, 3)
        grid.placeBead(0, 0, 3)
        assertEquals(1, grid.beadCount)
    }

    @Test
    fun `placeBead at invalid position should be ignored`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(10, 10, 1)
        assertEquals(0, grid.beadCount)
    }

    @Test
    fun `eraseBead should remove bead`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(1, 1, 4)
        grid.eraseBead(1, 1)
        assertNull(grid[1, 1])
        assertEquals(0, grid.beadCount)
    }

    @Test
    fun `eraseBead on empty cell should be no-op`() {
        val grid = BeadGrid(5, 5)
        grid.eraseBead(0, 0)
        assertEquals(0, grid.beadCount)
    }

    @Test
    fun `undo should restore previous state`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(0, 0, 1)
        grid.undo()
        assertNull(grid[0, 0])
        assertTrue(grid.canRedo)
    }

    @Test
    fun `redo should reapply undone action`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(0, 0, 1)
        grid.undo()
        grid.redo()
        assertEquals(1, grid[0, 0])
    }

    @Test
    fun `clear should remove all beads`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(0, 0, 1)
        grid.placeBead(1, 1, 2)
        grid.clear()
        assertEquals(0, grid.beadCount)
    }

    @Test
    fun `fillAt should flood fill connected region`() {
        val grid = BeadGrid(5, 5)
        grid.placeBead(0, 0, 1)
        grid.placeBead(0, 1, 1)
        grid.placeBead(0, 2, 1)
        grid.fillAt(0, 0, 2)
        assertEquals(2, grid[0, 0])
        assertEquals(2, grid[0, 1])
        assertEquals(2, grid[0, 2])
    }

    @Test
    fun `get should return null for out of bounds indices`() {
        val grid = BeadGrid(5, 5)
        assertNull(grid[-1, 0])
        assertNull(grid[0, -1])
        assertNull(grid[5, 0])
    }
}
