package com.guiadeacessibilidade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.guiadeacessibilidade.ui.viewmodel.AddCheckListUiState
import com.guiadeacessibilidade.ui.viewmodel.AddCheckListViewModel
import com.guiadeacessibilidade.ui.viewmodel.DropDownSubCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCheckListScreen(
    onAddCheckListSuccess: () -> Unit,
    viewModel: AddCheckListViewModel,
    modifier: Modifier = Modifier
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val checkListName by viewModel.checkListName.collectAsStateWithLifecycle()
    val checkListDescription by viewModel.checkListDescription.collectAsStateWithLifecycle()
    val dropDownSubcategory by viewModel.dropDownSubCategory.collectAsStateWithLifecycle()

    val options = listOf("sub_categ_1_1", "sub_categ_1_2", "sub_categ_1_3", "sub_categ_1_4", "sub_categ_1_5")

    val snackBarHostState = remember { SnackbarHostState() }

    val isLoading = uiState is AddCheckListUiState.Loading

    //var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState is AddCheckListUiState.Error){
            snackBarHostState.showSnackbar((uiState as AddCheckListUiState.Error).message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Adicionar CheckList",
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = checkListName,
                    onValueChange = {
                        viewModel.onCheckListNameChanged(it)
                    },
                    label = { Text("Nome:") },
                    enabled = !isLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = checkListDescription,
                    onValueChange = {
                        viewModel.onCheckListDescriptionChanged(it) },
                    label = { Text("Descrição:") },
                    enabled =  !isLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = dropDownSubcategory.expanded,
                    onExpandedChange = { viewModel.onDropDownSubCategoryExpandedChanged(!dropDownSubcategory.expanded)}
                ) {
                    TextField(
                        value = dropDownSubcategory.optionSelected,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownSubcategory.expanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )
                    ExposedDropdownMenu(
                        expanded = dropDownSubcategory.expanded,
                        onDismissRequest = {
                            viewModel.onDropDownSubCategoryExpandedChanged(false)
                        }
                    ){
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = MaterialTheme.colorScheme.onSurface) },
                                onClick = {
                                    viewModel.onDropDownSubCategoryNewOptionChanged(option)
                                    viewModel.onDropDownSubCategoryExpandedChanged(false)
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.addChecklist(onSuccess = onAddCheckListSuccess)
                    },
                    enabled = !isLoading,
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 0, green = 32, blue = 96),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    else {
                        Text("Criar CheckList")
                    }
                }

            }
        }


    }
}













