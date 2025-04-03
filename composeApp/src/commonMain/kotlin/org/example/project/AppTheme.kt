package org.example.project

import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = MaterialTheme.colors.primary),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )
    ) {
        content()
    }
}

@Composable
fun getColorsTheme(): ColorsDarkTheme {
    val isDarkMode=false
    val purple = Color(0xFF6200EE)
    val colorExpenseItem = if(isDarkMode) Color.Red else Color.Blue
    val backgroundColor = if(isDarkMode) Color.Black else Color.White
    val textColor = if(isDarkMode) purple else Color.Black
    val addIconColor = if(isDarkMode) purple else Color.Black.copy(alpha = 0.2f)

    return ColorsDarkTheme(
        purple = purple,
        colorExpenseItem = colorExpenseItem,
        backgroundColor = backgroundColor,
        textColor = textColor,
        addIconColor = addIconColor,
        colorArrowRound = purple
    )
}

data class ColorsDarkTheme(
    val purple: Color,
    val colorExpenseItem: Color,
    val backgroundColor: Color,
    val textColor: Color,
    val addIconColor: Color,
    val colorArrowRound: Color
)

