package org.example.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.getColorsTheme
import org.example.project.model.Expense
import org.example.project.model.ExpenseCategory


@Composable
fun ExpenseDetailScreen(
    expenseToEdit: Expense? = null,
    categoryList: List<ExpenseCategory>,
    addExpenseAndNavigateBack: (Expense) -> Unit
) {
    val colors = getColorsTheme()
    val price by remember { mutableStateOf(expenseToEdit?.amount ?: 0.0) }
    val description by remember { mutableStateOf(expenseToEdit?.description ?: "") }
    var expenseCategory by remember { mutableStateOf(expenseToEdit?.category?.name ?: "") }
    var categorySelectecd by remember {
        mutableStateOf(
            expenseToEdit?.category?.name ?: "Selecciona una categoria"
        )
    }
    val sheeetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(sheeetState.targetValue) {
        if (sheeetState.targetValue == ModalBottomSheetValue.Expanded) {
            keyboardController?.hide()
        } else {
            keyboardController?.show()
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheeetState,
        sheetContent = {
            CategoryBootomSheet(categoryList) {
                expenseCategory=it.name
                categorySelectecd= it.name
                scope.launch {
                    sheeetState.hide()
                }
            }
        }
    ){

    }
}

@Composable
fun CategoryBootomSheet(
    category: List<ExpenseCategory>,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(category.size) {
            CategoryItem(category[it], onCategorySelected)
        }

    }
}

@Composable
fun CategoryItem(category: ExpenseCategory, onCategorySelectect: (ExpenseCategory) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
            .clickable { onCategorySelectect(category) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = category.icon,
            modifier = Modifier.size(40.dp).clip(CircleShape).padding(8.dp),
            contentDescription = "category icon",
            contentScale = ContentScale.Crop
        )
        Text(text = category.name)
    }

}