package com.trex.laxmiemi.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R
import com.trex.laxmiemi.utils.CommonConstants

// -- Logout Button --
@Composable
fun RexActionButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
    ) {
        Text(
            text,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
fun RoundedCardText(dealerCode: String) {
    Row(
        modifier =
            Modifier
                .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = dealerCode,
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 22.sp,
                    color = colorResource(id = R.color.primary),
                ),
        )
    }
}

@Composable
fun TitleText(
    text: String,
    modifier: Modifier,
    color: Color,
) {
    Text(
        text = text,
        style =
            TextStyle(
                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                fontSize = 20.sp,
                color = color,
            ),
        modifier = modifier,
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier =
            Modifier
                .padding(16.dp),
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun MyTopAppBar() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = colorResource(id = R.color.blue_600)),
    ) {
        Box(
            modifier =
                Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.Center),
        ) {
            Column {
                Divider()
                TitleText(
                    text = CommonConstants.appName,
                    modifier =
                        Modifier
                            .padding(0.dp)
                            .fillMaxWidth(),
                    color = Color.White,
                )
                Divider()
            }
        }
    }
}
