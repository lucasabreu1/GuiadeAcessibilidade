package com.guiadeacessibilidade.ui.components

import android.view.MenuItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.guiadeacessibilidade.ui.viewmodel.UserUiState
import com.guiadeacessibilidade.ui.viewmodel.WelcomeUiState
import com.guiadeacessibilidade.ui.viewmodel.WelcomeViewModel
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen (
    viewModel: WelcomeViewModel,
    onMenuSelected: (String,String, String) -> Unit,
    onCheckListFeatureSelected: (String, String) -> Unit,
    onLogoutSuccess: () -> Unit,
    modifier: Modifier = Modifier
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiUserState by viewModel.userUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadMenus()
        viewModel.getCurrentUser()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Início",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Column()
                        {
                            Text(
                                text = "Olá",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light
                            )
                            Box {
                                when (val state = uiUserState){
                                    is UserUiState.Loading -> {

                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                    is UserUiState.Success -> {
                                        Text(
                                            text = state.user.name,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    is UserUiState.Error -> {
                                        Text(
                                            text = "Usuario",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {

        }
    ) { paddingValues ->

        Box(
            modifier.padding(paddingValues),
        ) {
            when (val state = uiState) {
                is WelcomeUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is WelcomeUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.menuList) { menu ->
                            MenuItemCard(
                                name = menu.name,
                                svgContent = menu.svgContent,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clickable {
                                        onMenuSelected(
                                            menu.id,
                                            menu.name,
                                            menu.collection
                                        )
                                    }
                            )
                        }

                        item {
                            Spacer(modifier = modifier.height(10.dp))
                        }

                        item {
                            Spacer(modifier = modifier.height(10.dp))
                        }
                        item {
                            Button(
                                onClick = {
                                    viewModel.logoutCurrentUser(onLogoutSuccess = onLogoutSuccess)
                                },
                                //enabled = !isLoading,
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(red = 0, green = 32, blue = 96),
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                            ){
                                    Text("Sair")
                            }
                        }
                    }
                }

                is WelcomeUiState.Error -> {
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
                            viewModel.loadMenus()
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
fun MenuItemCard(
    name: String,
    svgContent: String,
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
            //verticalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                //modifier = Modifier.background(Color.Yellow),
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                minLines = 2,
                maxLines = 2,
                color = Color(0xFF003366)
            )
            Spacer(modifier = Modifier.height(16.dp))
            SvgIconItem2(
                svgContentString = svgContent,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun SvgIconItem2(
    svgContentString: String,
    modifier: Modifier
) {
    // Converte a string XML em ByteArray para o Coil processar
    val svgByteArray = svgContentString.toByteArray(StandardCharsets.UTF_8)

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(svgByteArray)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = "Ícone SVG do item",
        modifier = modifier
            .size(70.dp)
    )
}


