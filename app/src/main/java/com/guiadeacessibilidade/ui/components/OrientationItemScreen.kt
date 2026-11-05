package com.guiadeacessibilidade.ui.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.guiadeacessibilidade.domain.models.Orientation
import com.guiadeacessibilidade.ui.viewmodel.OrientationItemUiState
import com.guiadeacessibilidade.ui.viewmodel.OrientationItemViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrientationItemScreen(
    orientationId: String,
    viewModel: OrientationItemViewModel,
    modifier: Modifier = Modifier
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadOrientationById(orientationId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalhes da orientação",
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
                is OrientationItemUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is OrientationItemUiState.Success -> {
                    OrientationItemCard(
                        orientationItem = state.orientationItem,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }

                is OrientationItemUiState.Error -> {

                }
            }
        }
    }
}

@Composable
fun OrientationItemCard(
    orientationItem: Orientation,
    modifier: Modifier
){
    val scrollState = rememberScrollState()

    Column(
        modifier.verticalScroll(scrollState)
    ) {
        Text(
            //modifier = Modifier.background(Color.Red),
            text=orientationItem.name,
            style = MaterialTheme.typography.titleLarge.copy(
                hyphens = Hyphens.Auto,
                lineBreak = LineBreak.Simple,
                fontWeight = FontWeight.Bold
            ),
        )
//        Box(
//            Modifier.size(300.dp).background(Color.Red)
//        ){
//            Box(Modifier.size(200.dp).background(Color.Yellow)
//                .align(Alignment.Center)
//            )
//            Box(Modifier.size(80.dp).background(Color.Blue)
//                .align(Alignment.BottomEnd)
//                .padding(bottom = 20.dp)
//            ){
//                Text(text = "lucas",
//                    modifier = Modifier.align(Alignment.BottomEnd)
//
//                )
//            }
//        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            //modifier = Modifier.background(Color.Blue),
            text=orientationItem.description1,
            style = MaterialTheme.typography.bodyLarge.copy(
                hyphens = Hyphens.Auto,
                lineBreak = LineBreak.Simple
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        AsyncImage(
            model = orientationItem.imageUrl,
            contentDescription = "Ícone SVG do item",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            //modifier = Modifier.background(Color.Blue),
            text=orientationItem.description2,
            style = MaterialTheme.typography.bodyLarge.copy(
                hyphens = Hyphens.Auto,
                lineBreak = LineBreak.Simple
            )
        )
    }
}










