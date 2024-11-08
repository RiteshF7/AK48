import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.MainActivityViewModel
import com.trex.laxmiemi.R
import com.trex.laxmiemi.ui.components.ButtonActions
import com.trex.laxmiemi.ui.components.GridButton

@Composable
fun HomeScreen(homeScreenViewModel: MainActivityViewModel) {
    val dealerCode = homeScreenViewModel.dealerCode.value

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Header()
            ButtonGrid()
        }
    }
}

@Composable
fun Header() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 60.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                contentDescription = "",
                painter = painterResource(R.drawable.shield),
                modifier = Modifier.size(64.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            HeaderText()
        }
    }
    HorizontalDivider(thickness = 2.dp)
}

@Composable
fun HeaderText() {
    Column {
        Text(
            text = "SECURE EMI SHIELD",
            color = Color.White,
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
                    fontSize = 26.sp,
                ),
        )
        Text(
            text = "Secure EMI payments",
            color = Color.White,
            style =
                TextStyle(
                    fontFamily = FontFamily(Font(R.font.opensans_medium)),
                    fontSize = 15.sp,
                ),
        )
    }
}

//
// @Composable
// fun Header(dealerCode: String) {
//    Box(
//        modifier =
//            Modifier
//                .fillMaxWidth()
//                .background(Color(0xFF1F1F1F))
//                .padding(vertical = 16.dp, horizontal = 20.dp),
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.shield),
//                    contentDescription = null,
//                    modifier = Modifier.size(64.dp),
//                )
//                Column {
//                    Text(
//                        text = "SECURE EMI SHIELD",
//                        color = Color.White,
//                        style =
//                            TextStyle(
//                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
//                                fontSize = 26.sp,
//                            ),
//                    )
//                    Text(
//                        text = "Secure EMI payments",
//                        color = Color.White,
//                        style =
//                            TextStyle(
//                                fontFamily = FontFamily(Font(R.font.opensans_bold)),
//                                fontSize = 26.sp,
//                            ),
//                    )
//                }
//            }
//            Text(
//                text = "Dealer Code: $dealerCode",
//                color = Color.White,
//                style =
//                    TextStyle(
//                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
//                        fontSize = 26.sp,
//                    ),
//            )
//        }
//    }
// }

@Composable
fun ButtonGrid() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ActionButton("View EMI Shield")
            ActionButton("View EMI Details")
            ActionButton("View EMI Details")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ActionButton("View EMI Shield")
            ActionButton("View EMI Details")
            ActionButton("View EMI Details")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ActionButton("View EMI Shield")
            ActionButton("View EMI Details")
            ActionButton("View EMI Details")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ActionButton("View EMI Shield")
            ActionButton("View EMI Details")
            ActionButton("View EMI Details")
        }
    }
}

@Composable
fun ActionButton(text: String) {
    MenuItemCard(
        GridButton(
            icon = Icons.Default.Add,
            action = ButtonActions.AddCustomer,
            title = "Add \nsomething",
        ),
    ) { }
}

@Composable
fun MenuItemCard(
    item: GridButton,
    onClick: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)

    Card(
        modifier =
            Modifier
                .scale(scale)
                .clickable(
                    onClick = onClick,
                    onClickLabel = item.title,
                ),
        shape = RoundedCornerShape(8.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = Color(0xFF2A2A2A),
                contentColor = Color.White,
            ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Card(
                colors =
                    CardDefaults.cardColors(
                        containerColor = Color.Green.copy(alpha = 0.1f),
                        contentColor = Color.White,
                    ),
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = Color(0xFF00C853),
                    modifier = Modifier.size(32.dp),
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                textAlign = TextAlign.Center,
                text = item.title,
                style =
                    TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    ),
            )
        }
    }
}

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
                .padding(10.dp)
                .height(40.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.red_300),
                contentColor = Color.White,
            ),
    ) {
        Text(text)
    }
}
