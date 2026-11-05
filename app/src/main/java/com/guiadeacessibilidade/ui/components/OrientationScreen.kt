package com.guiadeacessibilidade.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.guiadeacessibilidade.domain.models.OrientationWithStatus
import com.guiadeacessibilidade.ui.viewmodel.OrientationUiEvent
import com.guiadeacessibilidade.ui.viewmodel.OrientationUiState
import com.guiadeacessibilidade.ui.viewmodel.OrientationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrientationScreen(
    subCategoryId: String,
    viewModel: OrientationViewModel,
    onOrientationSelected: (String, String) -> Unit,
    modifier: Modifier = Modifier
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadOrientationsBySubCategoryId(subCategoryId)
        viewModel.uiEvent.collect { event ->
            when (event) {
                is OrientationUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Orientações",
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
        ) {
            when (val state = uiState) {
                is OrientationUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is OrientationUiState.Success -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(1),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.orientations) { item ->
                            OrientationCard(
                                item = item,
                                onCheckedChange = { isChecked ->
                                    //viewModel.onToggleOrientation(item.orientation.id, isChecked)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clickable {
                                        onOrientationSelected(
                                            item.orientation.id,
                                            item.orientation.name
                                        )
                                    }
                            )
                        }
                    }
                }

                is OrientationUiState.Error -> {
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
                            viewModel.loadOrientationsBySubCategoryId(subCategoryId)
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
fun OrientationCard(
    item: OrientationWithStatus,
    onCheckedChange: (Boolean) -> Unit,
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
            SvgIconItem(
                item.orientation.svgIcon,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                text= item.orientation.shortName,
                modifier = Modifier
                    .weight(1f)
                    //.background(Color.LightGray)
            )
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { checked ->
                    onCheckedChange(checked)
                }
            )
        }
    }
}

var svgTest: String = "<svg width=\"60\" height=\"60\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xml:space=\"preserve\" overflow=\"hidden\"><g transform=\"translate(-536 -372)\"><path d=\"M548 378C544.691 378 542 380.691 542 384L542 420C542 423.309 544.691 426 548 426L555.5 426 555.5 415.5C555.5 412.191 558.191 409.5 561.5 409.5L578 409.5 578 393.984C578 392.391 577.372 390.862 576.247 389.737L566.253 379.753C565.128 378.628 563.609 378 562.016 378L548 378ZM572.516 394.5 563.75 394.5C562.503 394.5 561.5 393.497 561.5 392.25L561.5 383.484 572.516 394.5ZM561.5 413.625C560.469 413.625 559.625 414.469 559.625 415.5L559.625 427.5C559.625 428.531 560.469 429.375 561.5 429.375 562.531 429.375 563.375 428.531 563.375 427.5L563.375 424.875 564.5 424.875C567.603 424.875 570.125 422.353 570.125 419.25 570.125 416.147 567.603 413.625 564.5 413.625L561.5 413.625ZM564.5 421.125 563.375 421.125 563.375 417.375 564.5 417.375C565.531 417.375 566.375 418.219 566.375 419.25 566.375 420.281 565.531 421.125 564.5 421.125ZM573.5 413.625C572.469 413.625 571.625 414.469 571.625 415.5L571.625 427.5C571.625 428.531 572.469 429.375 573.5 429.375L576.5 429.375C579.191 429.375 581.375 427.191 581.375 424.5L581.375 418.5C581.375 415.809 579.191 413.625 576.5 413.625L573.5 413.625ZM575.375 425.625 575.375 417.375 576.5 417.375C577.119 417.375 577.625 417.881 577.625 418.5L577.625 424.5C577.625 425.119 577.119 425.625 576.5 425.625L575.375 425.625ZM583.625 415.5 583.625 427.5C583.625 428.531 584.469 429.375 585.5 429.375 586.531 429.375 587.375 428.531 587.375 427.5L587.375 423.375 590 423.375C591.031 423.375 591.875 422.531 591.875 421.5 591.875 420.469 591.031 419.625 590 419.625L587.375 419.625 587.375 417.375 590 417.375C591.031 417.375 591.875 416.531 591.875 415.5 591.875 414.469 591.031 413.625 590 413.625L585.5 413.625C584.469 413.625 583.625 414.469 583.625 415.5Z\" fill=\"#002060\"/></g></svg>"

















