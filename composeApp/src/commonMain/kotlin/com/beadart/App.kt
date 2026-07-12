// App.kt — bead-art entry point
package com.beadart

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.beadart.ui.screens.EditorScreen

@Composable
fun App() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        EditorScreen()
    }
}
