package org.example.project.navigation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import org.example.project.data.ExpenseManager
import org.example.project.data.ExpenseRepositoryImpl
import org.example.project.getColorsTheme
import org.example.project.presentation.ExpensesViewModel
import org.example.project.ui.ExpenseDetailScreen
import org.example.project.ui.ExpensesScreen

@Composable
fun Navigation(navigator: Navigator) {

    val color = getColorsTheme()

    val viewModel: ExpensesViewModel = remember {
        ExpensesViewModel(ExpenseRepositoryImpl(ExpenseManager))
    }

    NavHost(
        modifier = Modifier.background(color.backgroundColor),
        navigator = navigator,
        initialRoute = "/home"
    ) {
        scene(route = "/home") {
            val uiState by viewModel.uiStateFlow.collectAsState()
            ExpensesScreen(uiState) {
                navigator.navigate("/addExpense/${it.id}")
            }
        }

        scene(route = "/addExpense/{id}?") {
            val idFromPath = it.path<Long>("id")
            val isAddExpense = idFromPath?.let { id ->
                viewModel.getExpenseById(id) }
            ExpenseDetailScreen(isAddExpense, viewModel.getCategorys()){
                expense->
                if(isAddExpense==null){
                    viewModel.addExpense(expense)
                }else{
                    viewModel.editExpense(expense)
                }
                navigator.popBackStack()
            }


        }

    }


}