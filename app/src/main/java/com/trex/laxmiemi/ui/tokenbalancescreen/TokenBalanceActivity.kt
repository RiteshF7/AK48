package com.trex.laxmiemi.ui.tokenbalancescreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.R

class TokenBalanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: TokenBalanceViewModel by viewModels()
        setContent {
            Scaffold { padding ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(color = Color.Black.copy(alpha = 0.85f)),
                ) {
                    TokenBalanceScreen(vm)
                }
            }
        }
    }

    @Composable
    fun TokenBalanceScreen(vm: TokenBalanceViewModel) {
        val uiState by vm.uiState
        val context = LocalContext.current
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Token balance",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(20.dp))
            HorizontalDivider()
            Column(
                modifier = Modifier.padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Card(
                    shape = RoundedCornerShape(200.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = colorResource(R.color.primary).copy(alpha = 0.1f),
                            contentColor = colorResource(R.color.primary),
                        ),
                ) {
                    Text(
                        modifier = Modifier.padding(50.dp),
                        text = if (uiState.balance == "") "0" else uiState.balance,
                        style =
                            TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 132.sp,
                                color = colorResource(R.color.primary),
                            ),
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "TOKEN REMAINING",
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_bold)),
                            fontSize = 26.sp,
                            color = colorResource(id = R.color.white),
                        ),
                )
            }

            Row(
                Modifier.padding(20.dp).fillMaxWidth().weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TokenCountCard(uiState.balance, "Remaining\nToken", colorResource(R.color.primary))
                TokenCountCard(uiState.usedToken, "Used\nToken", colorResource(R.color.red_300))
            }

            AddToken(text = "Add Token", icon = Icons.Default.Add) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.setData(Uri.parse("tel:9910000163"))
                context.startActivity(intent)
            }
            Spacer(Modifier.padding(10.dp))
        }
    }

    @Composable
    fun TokenCountCard(
        count: String,
        title: String,
        color: Color,
    ) {
        if (!count.equals("")) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Card(
                    shape = RoundedCornerShape(100.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor = color.copy(alpha = 0.1f),
                            contentColor = colorResource(R.color.primary),
                        ),
                ) {
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = if (count.isBlank()) "0" else count,
                        style =
                            TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = color,
                            ),
                    )
                }
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = title,
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.opensans_medium)),
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.white),
                        ),
                )
            }
        }
    }

    @Composable
    fun AddToken(
        text: String,
        modifier: Modifier = Modifier,
        icon: ImageVector,
        onClick: () -> Unit,
    ) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .height(40.dp)
                    .clickable { onClick() }
                    .background(
                        color = colorResource(R.color.primary).copy(alpha = 0.8f),
                        shape = RoundedCornerShape(5.dp),
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = icon,
                    tint = Color.White,
                    contentDescription = "",
                    modifier = Modifier.size(15.dp),
                )
                Spacer(Modifier.padding(6.dp))
                Text(
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
