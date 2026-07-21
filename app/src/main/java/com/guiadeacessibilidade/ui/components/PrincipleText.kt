package com.guiadeacessibilidade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guiadeacessibilidade.ui.utils.ColorMapping

@Composable
fun PrincipleText(principle: String) {

    val backgroundColor: Color = when (principle.lowercase()) {
        in ColorMapping.principleBackgroundColor.keys -> {
            ColorMapping.principleBackgroundColor[principle.lowercase()] ?: Color.Gray
        }

        else -> Color.Gray
    }

    Text(
        text = principle.replaceFirstChar{ it.uppercase() },
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,

            ),
        modifier = Modifier
            .background(
                color = backgroundColor
            )
            .padding(5.dp)
            .fillMaxWidth(),
        color = Color.White
    )
}
