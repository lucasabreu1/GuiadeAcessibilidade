package com.guiadeacessibilidade.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SuccessCriteriaCard(
    name: String,
    level: String,
    principle: String,
    description: String
){
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ){
        Column (
            modifier = Modifier.padding(10.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    ),
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    shape = CircleShape,

                    ) {
                    Text(
                        text = "[${level}]",
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        style = TextStyle(
                            fontSize = 16.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            PrincipleText(principle)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = description,
                minLines = 5,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}