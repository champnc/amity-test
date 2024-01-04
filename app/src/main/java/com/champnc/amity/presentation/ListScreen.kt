package com.champnc.amity.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.champnc.amity.data.model.getBoolean


@Composable
fun ListScreen(
    viewModel: ListScreenViewModel
) {
    val state = viewModel.uiState.collectAsState()
    val item = viewModel.item

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        state.value.let {
            when(it) {
                is ListScreenViewModel.UiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(it.todoList) {
                            Card(
                                modifier = Modifier.clickable {
                                    viewModel.updateStatus(it)
                                },
                                colors = CardDefaults.cardColors()
                            ) {
                                Text(text = it.title)
                                if (it.status.getBoolean()) {
                                    Text(text = "Success")
                                } else {
                                    Text(text = "Not success")
                                }
                            }
                        }
                    }
                }

                else -> {}
            }
        }
        
    }
}

fun NavGraphBuilder.listScreen() {
    composable(
        route = "list"
    ) {
        val viewModel = hiltViewModel<ListScreenViewModel>()
        ListScreen(viewModel = viewModel)
    }
}