package com.beadart.data

import com.beadart.model.BeadGrid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface IDesignRepository {
    fun observeCurrentGrid(): Flow<BeadGrid?>
    suspend fun save(grid: BeadGrid)
    suspend fun load(): BeadGrid?
    suspend fun clear()
}

class InMemoryDesignRepository : IDesignRepository {
    private val mutex = Mutex()
    private val _grid = MutableStateFlow<BeadGrid?>(null)

    override fun observeCurrentGrid(): Flow<BeadGrid?> = _grid.asStateFlow()

    override suspend fun save(grid: BeadGrid) = mutex.withLock {
        _grid.value = grid
    }

    override suspend fun load(): BeadGrid? = mutex.withLock { _grid.value }

    override suspend fun clear() = mutex.withLock { _grid.value = null }
}
