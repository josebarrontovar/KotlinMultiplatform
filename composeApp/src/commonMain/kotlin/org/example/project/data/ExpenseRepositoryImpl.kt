package org.example.project.data

import org.example.project.domain.ExpenseRepository
import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory

class ExpenseRepositoryImpl(private val expenseManager: ExpenseManager) : ExpenseRepository {
    override fun getAllExpenses(): List<Expense> {
        return expenseManager.fakeExpenseList
    }

    override fun addExpense(expense: Expense) {
        expenseManager.addExpense(expense)
    }

    override fun editExpense(expense: Expense) {
        expenseManager.editExpense(expense)
    }

    override fun getAllCategories(): List<ExpenseCategory> {
        return expenseManager.getAllCategories()
    }

    override fun deleteExpense(expense: Expense) {
        expenseManager.deleteExpense(expense)
    }
}