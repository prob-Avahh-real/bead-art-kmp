// ColorPalette.kt — bead-art color definition
package com.beadart.model

data class BeadColor(val index: Int, val name: String, val hexColor: Long, val rgb: Triple<Int, Int, Int>)

val beadPalette = listOf(
    BeadColor(1, "White", 0xFFFFFFFF, Triple(255, 255, 255)),
    BeadColor(2, "Black", 0xFF212121, Triple(33, 33, 33)),
    BeadColor(3, "Red", 0xFFD32F2F, Triple(211, 47, 47)),
    BeadColor(4, "Pink", 0xFFE91E63, Triple(233, 30, 99)),
    BeadColor(5, "Orange", 0xFFFF9800, Triple(255, 152, 0)),
    BeadColor(6, "Yellow", 0xFFFFEB3B, Triple(255, 235, 59)),
    BeadColor(7, "Green", 0xFF4CAF50, Triple(76, 175, 80)),
    BeadColor(8, "Teal", 0xFF009688, Triple(0, 150, 136)),
    BeadColor(9, "Blue", 0xFF2196F3, Triple(33, 150, 243)),
    BeadColor(10, "Indigo", 0xFF3F51B5, Triple(63, 81, 181)),
    BeadColor(11, "Purple", 0xFF9C27B0, Triple(156, 39, 176)),
    BeadColor(12, "Brown", 0xFF795548, Triple(121, 85, 72)),
    BeadColor(13, "Grey", 0xFF9E9E9E, Triple(158, 158, 158)),
    BeadColor(14, "Gold", 0xFFFFD700, Triple(255, 215, 0)),
    BeadColor(15, "Silver", 0xFFC0C0C0, Triple(192, 192, 192)),
    BeadColor(16, "Coral", 0xFFFF6F61, Triple(255, 111, 97))
)

fun colorForIndex(index: Int): BeadColor? = beadPalette.find { it.index == index }
