package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.data.ExpenseManager
import org.example.project.presentation.ExpensesUiState
import org.example.project.ui.ExpensesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpensesScreenPreview() {
    ExpensesScreen(
        uiState = ExpensesUiState(
            expenses = ExpenseManager.fakeExpenseList,
            1203.0
        ), onExpenseClick = {})  // Pass an empty lambda for the click action
}
