package com.guiadeacessibilidade.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.guiadeacessibilidade.R
import com.guiadeacessibilidade.ui.viewmodel.CategoryUiState
import com.guiadeacessibilidade.ui.viewmodel.CategoryViewModel

@Composable
fun CategoryScreen (
    viewModel: CategoryViewModel,
    onCategorySelected: (String,String) -> Unit,
    modifier: Modifier = Modifier
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    Box(
        modifier,
    ){
        when (val state = uiState) {
            is CategoryUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is CategoryUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
//                    item {
//                        Text(
//                            text = "Selecione uma Categoria",
//                            style = MaterialTheme.typography.headlineSmall,
//                            modifier = Modifier.padding(bottom = 8.dp)
//                        )
//                    }

                    items(state.categories) { category ->
                        CategoryCard(
                            name = category.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clickable {
                                    onCategorySelected(
                                        category.id,
                                        category.name
                                    )
                                }
                        )

//                        ElevatedCard(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable { onCategorySelected(
//                                    category.id,
//                                    category.name
//                                ) }
//                        ) {
//                            Text(
//                                text = category.name,
//                                modifier = Modifier.padding(24.dp),
//                                style = MaterialTheme.typography.bodyLarge
//                            )
//                        }
                    }
                }
            }

            is CategoryUiState.Error -> {
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
                        viewModel.loadCategories()
                    }) {
                        Text("Tentar Novamente")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    name: String,
    modifier: Modifier = Modifier,
){

    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                //modifier = Modifier.background(Color.Yellow),
                text = name,
                style = MaterialTheme.typography.titleLarge,
                minLines = 2,
                maxLines = 2,
                color = Color(0xFF003366)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.imagem5),
                contentDescription = "Imagem de background - acessibilidade em materias didáticos",
                contentScale = ContentScale.Fit,
                //modifier = Modifier.align(Alignment.End)
                modifier = Modifier
                    .background(Color.Red)
            )
        }
    }
}




