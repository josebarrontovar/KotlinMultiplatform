package org.example.project.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.getColorsTheme
import org.example.project.model.Expense
import org.example.project.presentation.ExpensesUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun ExpensesScreen(uiState: ExpensesUiState,onExpenseClick: (expense: Expense) -> Unit) {
    val colors = getColorsTheme()
    LazyColumn(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        stickyHeader {
            Column(modifier = Modifier.background(colors.backgroundColor)) {
                expenseTotalHeader(uiState.total)
                allExpenseHeader()
            }
        }
        items(uiState.expenses) { expense ->
            expenseItem(expense, onExpenseClick = {onExpenseClick(it)})
        }
    }
}

@Composable
fun expenseItem(expense: Expense, onExpenseClick: (expense: Expense) -> Unit) {
    val colors = getColorsTheme()
    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp).clickable
        { onExpenseClick(expense) },
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(20.dp),
                color = colors.purple
            ) {
                Image(
                    imageVector = expense.category.icon,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.padding(8.dp),
                    contentDescription = "Imagen icono",
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f).padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = expense.category.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colors.textColor
                )
                Text(
                    text = expense.description,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colors.textColor
                )

            }
            Column(modifier = Modifier.padding(start = 10.dp).align(Alignment.CenterVertically)) {
                Text(
                    expense.amount.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colors.textColor
                )
            }
        }
    }
}

@Composable
fun allExpenseHeader() {
    val colors = getColorsTheme()
    Row(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "All expenses",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = colors.textColor
        )
        Button(
            shape = RoundedCornerShape(50.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            enabled = false
        ) {
            Text("View All")
        }
    }
}

@Composable
fun expenseTotalHeader(total: Double) {
    Card(shape = RoundedCornerShape(30), backgroundColor = Color.Black, elevation = 5.dp) {
        Box(
            modifier = Modifier.padding(10.dp).fillMaxWidth().height(130.dp).padding(8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                "$$total",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                "USD",
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}
