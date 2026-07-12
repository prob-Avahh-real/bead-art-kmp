# Bead Art — KMP

A bead art / pattern designer built with Kotlin Multiplatform + Compose Multiplatform.

Place, erase, and flood-fill colored beads on a 29x29 grid canvas with 16 vibrant colors and full undo/redo support.

## Architecture

```
model/          → BeadGrid (grid data, undo/redo, flood fill), ColorPalette
engine/         → BeadEngine (stats, flood fill analysis, grid validation)
data/           → IDesignRepository + InMemoryDesignRepository
composeApp/     → EditorScreen (zoomable canvas, toolbar, palette)
```

## Features
- 29x29 bead grid with 16 colors
- Place, erase, and flood fill tools
- Undo/redo stack
- Bead count statistics

## Build

```bash
./gradlew :shared:allTests
./gradlew :composeApp:assembleRelease
./gradlew :shared:linkDebugFrameworkIosArm64
```

## Tech Stack
- Kotlin 2.1.10, Compose Multiplatform 1.7.3
- Material3, kotlinx-coroutines
