package com.beadart.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ColorPaletteTest {

    @Test
    fun `beadPalette should have 16 colors`() {
        assertEquals(16, beadPalette.size)
    }

    @Test
    fun `colorForIndex should return correct color`() {
        val color = colorForIndex(1)
        assertNotNull(color)
        assertEquals("White", color.name)
    }

    @Test
    fun `colorForIndex should return null for invalid index`() {
        assertNull(colorForIndex(99))
    }

    @Test
    fun `each color should have unique index`() {
        val indices = beadPalette.map { it.index }.distinct()
        assertEquals(beadPalette.size, indices.size)
    }

    @Test
    fun `BeadColor should have valid RGB triple`() {
        beadPalette.forEach { bc ->
            val (r, g, b) = bc.rgb
            assert(r in 0..255)
            assert(g in 0..255)
            assert(b in 0..255)
        }
    }
}
