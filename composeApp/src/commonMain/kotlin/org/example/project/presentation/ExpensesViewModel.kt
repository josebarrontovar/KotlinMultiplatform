package org.example.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.ExpenseRepository
import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory

class ExpensesViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {

    private val uiState = MutableStateFlow(ExpensesUiState())
    val uiStateFlow = uiState.asStateFlow()
    private val allExpenses = expenseRepository.getAllExpenses()

    init {
        updateState()
    }

    private fun updateState() {
        viewModelScope.launch {
            uiState.update {
                it.copy(expenses = allExpenses, total = allExpenses.sumOf { it.amount })
            }

        }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.addExpense(expense)
            updateState()
        }
    }

    fun editExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.editExpense(expense)
            updateState()
        }

    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
            updateState()
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            expenseRepository.getAllCategories()
        }
    }

    fun getExpenseById(id: Long): Expense {
        return expenseRepository.getAllExpenses().first { it.id == id }
    }

    fun getCategorys(): List<ExpenseCategory>{
        return expenseRepository.getAllCategories()
    }


}

data class ExpensesUiState(
    val expenses: List<Expense> = emptyList(),
    val total: Double = 0.0
)
