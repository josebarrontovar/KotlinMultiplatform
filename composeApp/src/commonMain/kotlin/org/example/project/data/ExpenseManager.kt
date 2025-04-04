package org.example.project.data

import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory

object ExpenseManager {

    private var currentId = 1L

    val fakeExpenseList = mutableListOf(
        Expense(
            id = currentId++,
            amount = 300.0,
            category = ExpenseCategory.GROCERIES,
            description = "Expense description GROCERIES"
        ),
        Expense(
            id = currentId++,
            amount = 200.0,
            category = ExpenseCategory.PARTY,
            description = "Expense description PARTY"
        ),
        Expense(
            id = currentId++,
            amount = 300.0,
            category = ExpenseCategory.SNACKS,
            description = "Expense description SNACKS"
        ),
        Expense(
            id = currentId++,
            amount = 400.0,
            category = ExpenseCategory.COFFEE,
            description = "Expense description COFFEE"
        ),
        Expense(
            id = currentId++,
            amount = 500.0,
            category = ExpenseCategory.CAR,
            description = "Expense description"
        ),
        Expense(
            id = currentId++,
            amount = 600.0,
            category = ExpenseCategory.HOUSE,
            description = "Expense description"
        ),
        Expense(
            id = currentId++,
            amount = 700.0,
            category = ExpenseCategory.OTHER,
            description = "Expense description"
        )
    )

    fun addExpense(expense: Expense) {
        fakeExpenseList.add(expense.copy(id = currentId++))
    }

    fun editExpense(expense: Expense) {
        val index = fakeExpenseList.indexOfFirst {
            it.id == expense.id
        }
        if (index >= 0) {
            fakeExpenseList[index] = fakeExpenseList[index].copy(
                amount = expense.amount,
                category = expense.category,
                description = expense.description
            )

        }
    }

    fun getAllCategories(): List<ExpenseCategory> {
        return listOf(
            ExpenseCategory.GROCERIES,
            ExpenseCategory.PARTY,
            ExpenseCategory.SNACKS,
            ExpenseCategory.COFFEE,
            ExpenseCategory.CAR,
            ExpenseCategory.HOUSE,
            ExpenseCategory.OTHER
        )
    }

    fun deleteExpense(expense: Expense) {
        val index = fakeExpenseList.indexOfFirst {
            it.id == expense.id
        }
        if (index >= 0) {
            fakeExpenseList.removeAt(index)
        }
    }
}