package com.guiadeacessibilidade.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import java.nio.charset.StandardCharsets

@Composable
fun SvgIconItem(svgContentString: String) {
    // Converte a string XML em ByteArray para o Coil processar
    val svgByteArray = svgContentString.toByteArray(StandardCharsets.UTF_8)

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(svgByteArray)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = "Ícone SVG do item",
        modifier = Modifier.size(40.dp)
    )
}