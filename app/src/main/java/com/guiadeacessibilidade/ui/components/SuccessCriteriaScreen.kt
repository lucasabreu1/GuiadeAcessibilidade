package com.guiadeacessibilidade.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.ui.viewmodel.SuccessCriteriaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessCriteriaScreen(viewModel: SuccessCriteriaViewModel){

    val filteredSuccessCriteriaList by viewModel.filteredSuccessCriteriaList.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                ),
                title = {
                    Text(
                        text = "Guia de Acessibilidade",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp
                        )
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar{

                    // Left side: Standard structural action icons
                    IconButton(onClick = { /* Navigate Home */ }) {
                        Icon(Icons.Filled.Home,
                            contentDescription = "Localized description",
                            modifier = Modifier.size(30.dp))
                    }
                    IconButton(onClick = { /* Open Edit Mode */ }) {
                        Icon(Icons.Filled.Edit,
                            contentDescription = "Localized description",
                            modifier = Modifier.size(30.dp))
                    }
                    IconButton(onClick = { /* Open Settings */ }) {
                        Icon(Icons.Filled.Settings,
                            contentDescription = "Localized description",
                            modifier = Modifier.size(30.dp))

                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            SearchTextField(
                value = searchText,
                onValueChanged = { it ->
                    viewModel.onQueryChanged(it)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn (
            ){
                items(filteredSuccessCriteriaList){ item ->
                    SuccessCriteriaCard(
                        item.sucessCriteria.name,
                        item.sucessCriteria.level,
                        item.principle.name,
                        item.sucessCriteria.description
                    )
                }
            }
        }
    }
}