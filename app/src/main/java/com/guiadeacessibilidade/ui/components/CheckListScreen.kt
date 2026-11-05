package com.guiadeacessibilidade.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.guiadeacessibilidade.domain.models.Checklist
import com.guiadeacessibilidade.domain.models.OrientationWithStatus
import com.guiadeacessibilidade.ui.viewmodel.CheckListUiState
import com.guiadeacessibilidade.ui.viewmodel.CheckListViewModel
import com.guiadeacessibilidade.ui.viewmodel.OrientationUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckListScreen(
    viewModel: CheckListViewModel,
    onAddCheckListSelected: () -> Unit,
    onCheckListSelected: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadCheckLists()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddCheckListSelected()
                    println("FAB clicado!")
                }
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar item"
                    )
                    Text(
                        text = "Novo Checklist"
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Meus CheckLists",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {

        }
    ) { paddingValues ->

        Box(
            modifier.padding(paddingValues)
        ){
            when (val state = uiState) {
                is CheckListUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is CheckListUiState.Success -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(1),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.checkLists) { item ->

                            CheckListCard(
                                item = item,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clickable {
                                        onCheckListSelected(
                                            item.id,
                                            item.subCategoryId
                                        )
                                    }
                            )
                        }
                    }
                }

                is CheckListUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            //viewModel.loadOrientationsBySubCategoryId(subCategoryId)
                        }) {
                            Text("Tentar Novamente")

                        }
                    }
                }

            }
        }

    }
}

@Composable
fun CheckListCard(
    item: Checklist,
    modifier: Modifier
){
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .fillMaxWidth()
        )
        {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Adicionar item"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                text = item.name,
                modifier = Modifier
                    .weight(1f)
                //.background(Color.LightGray)
            )
//            Checkbox(
//                checked = item.isCompleted,
//                onCheckedChange = { checked ->
//                    onCheckedChange(checked)
//                }
//            )
        }
    }
}









