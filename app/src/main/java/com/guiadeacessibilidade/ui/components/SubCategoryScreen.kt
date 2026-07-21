package com.guiadeacessibilidade.ui.components

import android.graphics.Paint
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.guiadeacessibilidade.domain.models.SubCategory
import com.guiadeacessibilidade.ui.viewmodel.SubCategoryUiState
import com.guiadeacessibilidade.ui.viewmodel.SubCategoryViewModel

@Composable
fun SubCategoryScreen (
    categoryId: String,
    viewModel: SubCategoryViewModel,
    onSubCategorySelected: (String,String) -> Unit,
    modifier: Modifier = Modifier
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadSubCategoriesByCategoryId(categoryId)
    }

    Box(
        modifier,
        //contentAlignment = Alignment.Center
    ){
        when (val state = uiState) {
            is SubCategoryUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            is SubCategoryUiState.Success -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Fixed(2),

                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item (
                        span = { GridItemSpan(maxLineSpan) }
                    ){
                        Text(
                            text = "Selecione uma SubCategoria",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(state.subCategories) { subCategory ->
                        SubCategoryCard(
                            name = subCategory.name,
                            svgContent = svgTest,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clickable { onSubCategorySelected(
                                    subCategory.id,
                                    subCategory.name
                                ) }
                        )
//                        ElevatedCard(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable { onSubCategorySelected(
//                                    subCategory.id,
//                                    subCategory.name
//                                ) }
//                        ) {
//                            Text(
//                                text = subCategory.name,
//                                minLines = 2,
//                                maxLines = 2,
//                                overflow = TextOverflow.Ellipsis,
//                                modifier = Modifier.padding(24.dp),
//                                style = MaterialTheme.typography.bodyLarge
//                            )
//                        }
                    }
                }
            }

            is SubCategoryUiState.Error -> {
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
                        viewModel.loadSubCategoriesByCategoryId(categoryId)
                    }) {
                        Text("Tentar Novamente")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SubCategoryCard(
    name: String,
    svgContent: String,
    modifier: Modifier
){
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.Home,
                contentDescription = "Icone de teste"
            )
//            AsyncImage(
//                model = "https://picsum.photos/200/300",
//                contentDescription = ""
//            )
            Text(

                text = name,
//                minLines = 2,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier.padding(24.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


var svgTest: String = ""














