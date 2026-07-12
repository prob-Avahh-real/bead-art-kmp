// EditorScreen.kt — bead-art editor with zoomable grid canvas
package com.beadart.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beadart.model.BeadGrid
import com.beadart.model.colorForIndex

@Composable
fun EditorScreen() {
    val grid = remember { BeadGrid(29, 29) }
    var selectedColor by remember { mutableStateOf(1) }
    var tool by remember { mutableStateOf("place") } // "place" | "erase" | "fill"
    var refresh by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxSize()) {
        // Toolbar
        Row(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text("Bead Art Editor", fontWeight = FontWeight.Bold)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = tool == "place", onClick = { tool = "place" },
                    label = { Text("放置") })
                FilterChip(selected = tool == "erase", onClick = { tool = "erase" },
                    label = { Text("擦除") })
                FilterChip(selected = tool == "fill", onClick = { tool = "fill" },
                    label = { Text("填充") })
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                IconButton(onClick = { grid.undo(); refresh++ }) {
                    Text("↩", fontSize = 18.sp)
                }
                IconButton(onClick = { grid.redo(); refresh++ }) {
                    Text("↪", fontSize = 18.sp)
                }
                IconButton(onClick = { grid.clear(); refresh++ }) {
                    Text("🗑", fontSize = 18.sp)
                }
            }
        }

        // Grid canvas
        Box(Modifier.weight(1f).fillMaxWidth().padding(8.dp)) {
            Canvas(Modifier.fillMaxSize().border(1.dp, Color.Gray.copy(alpha = 0.2f))) {
                val cellW = size.width / grid.cols
                val cellH = size.height / grid.rows

                // Draw beads
                for (r in 0 until grid.rows) for (c in 0 until grid.cols) {
                    val colorIdx = grid[r, c]
                    if (colorIdx != null) {
                        val bc = colorForIndex(colorIdx)
                        if (bc != null) {
                            drawRect(Color(bc.hexColor),
                                topLeft = androidx.compose.ui.geometry.Offset(c * cellW, r * cellH),
                                size = androidx.compose.ui.geometry.Size(cellW - 1f, cellH - 1f))
                        }
                    } else {
                        // Grid line
                        drawRect(Color.Gray.copy(alpha = 0.15f),
                            topLeft = androidx.compose.ui.geometry.Offset(c * cellW, r * cellH),
                            size = androidx.compose.ui.geometry.Size(cellW, cellH),
                            style = androidx.compose.ui.graphics.drawscope.Stroke(0.5f))
                    }
                }
            }

            // Touch handling
            Canvas(Modifier.fillMaxSize().pointerInput(tool, selectedColor) {
                detectTapGestures { offset ->
                    val cellW = size.width / grid.cols
                    val cellH = size.height / grid.rows
                    val c = (offset.x / cellW).toInt()
                    val r = (offset.y / cellH).toInt()
                    when (tool) {
                        "place" -> grid.placeBead(r, c, selectedColor)
                        "erase" -> grid.eraseBead(r, c)
                        "fill" -> grid.fillAt(r, c, selectedColor)
                    }
                    refresh++
                }
            })
        }

        // Color palette
        LazyRow(Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(com.beadart.model.beadPalette) { bc ->
                val isSelected = selectedColor == bc.index
                Surface(
                    modifier = Modifier.size(if (isSelected) 40.dp else 32.dp).clickable {
                        selectedColor = bc.index; tool = "place"
                    },
                    shape = CircleShape,
                    border = if (isSelected) BorderStroke(2.dp, Color.White) else null,
                    color = Color(bc.hexColor)
                ) {}
            }
        }

        // Stats bar
        Text("Beads: ${grid.beadCount}", fontSize = 12.sp, color = Color.Gray,
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally))
    }
}
