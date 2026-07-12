package com.beadart.data

import com.beadart.model.BeadGrid
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class InMemoryDesignRepositoryTest {

    @Test
    fun `save and load should return saved grid`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryDesignRepository()
        val grid = BeadGrid(10, 10)
        grid.placeBead(0, 0, 3)
        repo.save(grid)
        val loaded = repo.load()
        assertNotNull(loaded)
        assertEquals(10, loaded.rows)
        assertEquals(3, loaded[0, 0])
    }

    @Test
    fun `load should return null when empty`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryDesignRepository()
        assertNull(repo.load())
    }

    @Test
    fun `clear should remove saved grid`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryDesignRepository()
        repo.save(BeadGrid(5, 5))
        repo.clear()
        assertNull(repo.load())
    }

    @Test
    fun `observeCurrentGrid should emit saved grid`() = kotlinx.coroutines.test.runTest {
        val repo = InMemoryDesignRepository()
        val grid = BeadGrid(3, 3)
        repo.save(grid)
        val observed = repo.observeCurrentGrid()
        assertEquals(grid, observed)
    }
}
