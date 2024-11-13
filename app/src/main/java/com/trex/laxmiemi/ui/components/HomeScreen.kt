import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.laxmiemi.MainActivityViewModel
import com.trex.laxmiemi.R
import com.trex.laxmiemi.ui.components.ButtonActions
import com.trex.laxmiemi.ui.components.GridButton
import com.trex.laxmiemi.ui.devicescreen.DevicesActivity
import com.trex.laxmiemi.ui.profilescreen.ProfileActivity
import com.trex.laxmiemi.ui.qrcodescreen.ScanQrActivity
import com.trex.laxmiemi.ui.tokenbalancescreen.TokenBalanceActivity
import com.trex.laxmiemi.ui.videoplayerscreen.VideoPlayerActivity
import com.trex.rexnetwork.domain.firebasecore.firesstore.ShopFirestore
import com.trex.rexnetwork.utils.SharedPreferenceManager
import com.trex.rexnetwork.utils.startMyActivity

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
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Header()
            ButtonGrid()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun Header() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 50.dp),
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

/*
scan qr
all customers
user profile
Balance keys
call for assistance
installation video

*/

private val homeGridList =
    listOf(
        GridButton(
            icon = Icons.Default.QrCode,
            action = ButtonActions.SES20QR,
            title = "Scan QR Code",
        ),
        GridButton(
            icon = Icons.Default.List,
            action = ButtonActions.TotalCustomer,
            title = "All Devices",
        ),
        GridButton(
            icon = Icons.Default.VpnKey,
            action = ButtonActions.tokenBalance,
            title = "Token balance",
        ),
        GridButton(
            icon = Icons.Default.Videocam,
            action = ButtonActions.InstallationVideo,
            title = "Video Tutorial",
        ),
        GridButton(
            icon = Icons.Default.Call,
            action = ButtonActions.CallForService,
            title = "Call Support",
        ),
        GridButton(
            icon = Icons.Default.Person,
            action = ButtonActions.UserProfile,
            title = "User profile",
        ),
    )

@Composable
fun ButtonGrid() {
    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(homeGridList) { item ->
            MenuItemCard(
                item,
            ) { action ->
                when (action) {
                    ButtonActions.tokenBalance -> {
                        context.startMyActivity(TokenBalanceActivity::class.java)
                    }

                    ButtonActions.SES20QR -> {
                        SharedPreferenceManager(context).getShopId()?.let { shopId ->
                            ShopFirestore().getTokenBalanceList(shopId) { tokenBalanceList ->
                                if (tokenBalanceList.isEmpty()) {
                                    context.startMyActivity(TokenBalanceActivity::class.java)
                                    Toast.makeText(
                                        context,
                                        "Low token balance!\n PLease buy token to proceed!",
                                        Toast.LENGTH_LONG,
                                    )
                                } else {
                                    context.startMyActivity(ScanQrActivity::class.java)
                                }
                            }
                        }
                    }

                    ButtonActions.TotalCustomer -> {
                        val intent = Intent(context, DevicesActivity::class.java)
                        context.startActivity(intent)
                    }

                    ButtonActions.CallForService -> {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.setData(Uri.parse("tel:9910000163"))
                        context.startActivity(intent)
                    }

                    ButtonActions.InstallationVideo -> {
                        val intent =
                            Intent(context, VideoPlayerActivity::class.java)
                        context.startActivity(intent)
                    }

                    ButtonActions.UserProfile -> {
                        context.startActivity(
                            Intent(context, ProfileActivity::class.java),
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun MenuItemCard(
    item: GridButton,
    modifier: Modifier = Modifier, // Added modifier parameter
    onClick: (ButtonActions) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier =
            modifier
                .clickable { onClick(item.action) }
                .padding(4.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            colors =
                CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f),
                    contentColor = Color.Green,
                ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier =
                    Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
            ) {
                Card(
                    colors =
                        CardDefaults.cardColors(
                            containerColor = Color.Green.copy(alpha = 0.1f),
                            contentColor = Color.Green,
                        ),
                ) {
                    Box(Modifier.padding(10.dp)) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "",
                            modifier = Modifier.size(35.dp),
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = item.title,
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontSize = 13.sp,
                            color = colorResource(id = R.color.white),
                        ),
                )
            }
        }
    }
}

@Composable
fun RexActionButton(
    text: String,
    onClick: () -> Unit,
) {
    Box(Modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            modifier =
                Modifier
                    .padding(10.dp)
                    .height(40.dp)
                    .align(Alignment.BottomEnd),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.red_300),
                    contentColor = Color.White,
                ),
        ) {
            Text(text)
        }
    }
}
