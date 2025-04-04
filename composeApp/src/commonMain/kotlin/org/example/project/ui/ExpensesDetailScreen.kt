package org.example.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    var price by remember { mutableStateOf(expenseToEdit?.amount ?: 0.0) }
    val description by remember { mutableStateOf(expenseToEdit?.description ?: "") }
    var expenseCategory by remember { mutableStateOf(expenseToEdit?.category?.name ?: "") }
    var categorySelectecd by remember {
        mutableStateOf(
            expenseToEdit?.category?.name ?: "Selecciona una categoria"
        )
    }
    val sheeetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val keyboardController = LocalSoftwareKeyboardController?.current
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
                expenseCategory = it.name
                categorySelectecd = it.name
                scope.launch {
                    sheeetState.hide()
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ExpenseAmount(
                priceContent = price,
                onPriceChange = { price = it },
                keyboardController = keyboardController
            )
            Spacer(modifier = Modifier.height(30.dp))


        }
    }
}

@Composable
private fun ExpenseAmount(
    priceContent: Double,
    onPriceChange: (Double) -> Unit,
    keyboardController: SoftwareKeyboardController?
) {
    val colors = getColorsTheme()
    var text = remember { mutableStateOf("$priceContent") }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Amount")
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text.value,
            )
            TextField(
                modifier = Modifier.weight(1f),
                value = text.value,
                onValueChange = { newText ->
                    val numericText = newText.filter { it.isDigit() || it == '.' }
                    text.value = if (numericText.isNotEmpty()) {
                        try {
                            val newValue = numericText.toDouble()
                            onPriceChange(newValue)
                            numericText
                        } catch (e: NumberFormatException) {
                            ""
                        }

                    } else {
                        onPriceChange(0.0)
                        ""
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colors.textColor,
                    backgroundColor = colors.backgroundColor
                ),
            )
            Text(
                text = "USD",
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(end = 16.dp)
            )

        }
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