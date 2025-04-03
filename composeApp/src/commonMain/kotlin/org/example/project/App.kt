package org.example.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moe.tlaster.precompose.PreComposeApp  // Import PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator

import org.example.project.data.TitleTopBar
import org.example.project.navigation.Navigation

@Composable
fun App() {
    PreComposeApp {
        val colors = getColorsTheme()
        AppTheme {
            val navigator = rememberNavigator()
            val titleTopBar = getTitleTopBar(navigator)
            val isEditOrAddExpenses = titleTopBar != TitleTopBar.DASHBOARD.title
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        elevation = 0.dp,
                        title = {
                            Text(
                                text = titleTopBar,
                                style = TextStyle(fontSize = 20.sp, color = colors.textColor)

                            )
                        }, navigationIcon = {
                            if (isEditOrAddExpenses) {
                                IconButton(onClick = { navigator.popBackStack() }) {
                                    Icon(
                                        modifier = Modifier.padding(16.dp),
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Menu",
                                        tint = colors.textColor
                                    )
                                }
                            } else {
                                IconButton(onClick = {}) {
                                    Icon(
                                        modifier = Modifier.padding(16.dp),
                                        imageVector = Icons.Default.Apps,
                                        contentDescription = "Menu",
                                        tint = colors.textColor
                                    )
                                }
                            }

                        },
                        backgroundColor = colors.backgroundColor
                    )
                },
                floatingActionButton = {
                    if (!isEditOrAddExpenses) {
                        FloatingActionButton(
                            modifier = Modifier.padding(1.dp),
                            onClick = { navigator.navigate("/addExpense") },
                            shape = RoundedCornerShape(15),
                            backgroundColor = colors.addIconColor,
                            contentColor = colors.textColor
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Menu"
                            )
                        }
                    }
                }
            ) {
                Box() {
                    Navigation(navigator)
                }
            }
        }
    }
}

@Composable
fun getTitleTopBar(navigator: Navigator): String {
    var titleTopBar = TitleTopBar.DASHBOARD
    val isOnAddExpenses =
        navigator.currentEntry.collectAsState(null).value?.route?.route?.equals("/addExpense/{id}?")
            ?: false
    if (isOnAddExpenses) {
        titleTopBar = TitleTopBar.ADD_EXPENSE
    }

    val isOnEditExpenses =
        navigator.currentEntry.collectAsState(null).value?.path<Long>("id") != null
    if (isOnEditExpenses) {
        titleTopBar = TitleTopBar.EDIT_EXPENSE
    }

    return titleTopBar.title
}