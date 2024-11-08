//package com.trex.laxmiemi.ui.components
//
//import android.content.Context
//import android.content.Intent
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.trex.laxmiemi.MainActivityViewModel
//import com.trex.laxmiemi.R
//import com.trex.laxmiemi.utils.AppTypography
//
//enum class ButtonActions {
//    AddCustomer,
//    SES20QR,
//    TotalCustomer,
//    BalanceKeys,
//    CallForService,
//    InstallationVideo,
//    OldQR,
//    AppShare,
//    UserProfile,
//}
//
//data class GridButton(
//    val icon: ImageVector,
//    val action: ButtonActions,
//    val title: String,
//)
//
//@Composable
//fun HomeScreen(homeScreenViewModel: MainActivityViewModel) {
//    val scrollState = rememberScrollState()
//    val dealerCode = homeScreenViewModel.dealerCode.observeAsState("------")
//    val localContext = LocalContext.current
//
//    MaterialTheme(typography = AppTypography) {
//        Box(
//            modifier =
//                Modifier
//                    .background(Color.Black.copy(alpha = 0.85f))
//                    .fillMaxSize(),
//        ) {
//            Column(
//                modifier =
//                    Modifier
//                        .fillMaxWidth()
//                        .verticalScroll(scrollState),
//            ) {
//                Header()
//                DealerCode(dealerCode.value)
//                ButtonGrid { action ->
//                    // Handle button click
//                    when (action) {
//                        else -> {}
////                        ButtonActions.AddCustomer -> startMyActivity(localContext, AddCustomerActivity::class.java)
//                        // Handle other actions
//                    }
//                }
//                Spacer(modifier = Modifier.weight(1f))
//                RexActionButton("Logout") {
//                    homeScreenViewModel.signOut()
//                }
//            }
//        }
//    }
//}
//
//private fun <T> startMyActivity(
//    localContext: Context,
//    activityClass: Class<T>,
//) {
//    localContext.startActivity(Intent(localContext, activityClass))
//}
//
//@Composable
//fun DealerCode(dealerCode: String) {
//    Box(
//        modifier =
//            Modifier
//                .fillMaxWidth()
//                .height(70.dp)
//                .background(Color(0xFF1F1F1F)),
//    ) {
//        Row(
//            modifier =
//                Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.Center)
//                    .padding(horizontal = 20.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Text(
//                text = "Dealer Code: $dealerCode",
//                color = Color.White,
//                style =
//                    TextStyle(
//                        fontFamily = FontFamily(Font(R.font.opensans_bold)),
//                        fontSize = 20.sp,
//                    ),
//            )
//        }
//    }
//}
//
//@Composable
//fun Header() {
//    Box(
//        modifier =
//            Modifier
//                .fillMaxWidth()
//                .padding(vertical = 80.dp),
//    ) {
//        Row(
//            modifier = Modifier.align(Alignment.Center),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            Image(
//                contentDescription = "",
//                painter = painterResource(R.drawable.shield),
//                modifier = Modifier.size(64.dp),
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            HeaderText()
//        }
//    }
//}
//
//@Composable
//fun HeaderText() {
//    Column {
//        Text(
//            text = "SECURE EMI SHIELD",
//            color = Color.White,
//            style =
//                TextStyle(
//                    fontFamily = FontFamily(Font(R.font.opensans_bold)),
//                    fontSize = 26.sp,
//                ),
//        )
//        Text(
//            text = "Secure EMI payments",
//            color = Color.White,
//            style =
//                TextStyle(
//                    fontFamily = FontFamily(Font(R.font.opensans_medium)),
//                    fontSize = 15.sp,
//                ),
//        )
//    }
//}
//
//@Composable
//fun ButtonGrid(onClick: (action: ButtonActions) -> Unit) {
//    val menuItems =
//        listOf(
//            GridButton(Icons.Default.Add, ButtonActions.AddCustomer, "Register new customer"),
//            GridButton(Icons.Default.QrCode, ButtonActions.SES20QR, "Generate QR code"),
//            GridButton(Icons.Default.Group, ButtonActions.TotalCustomer, "View all customers"),
//            GridButton(Icons.Default.VpnKey, ButtonActions.BalanceKeys, "Manage keys"),
//            GridButton(Icons.Default.Call, ButtonActions.CallForService, "Get support"),
//            GridButton(Icons.Default.PlayArrow, ButtonActions.InstallationVideo, "Watch guide"),
//            GridButton(Icons.Default.QrCodeScanner, ButtonActions.OldQR, "Legacy QR codes"),
//            GridButton(Icons.Default.Share, ButtonActions.AppShare, "Share application"),
//            GridButton(Icons.Default.Person, ButtonActions.UserProfile, "Manage account"),
//        )
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = Modifier.padding(16.dp),
//    ) {
//        items(menuItems) { item ->
//            MenuItemCard(item) { onClick(item.action) }
//        }
//    }
//}
//
//@Composable
//fun MenuItemCard(
//    item: GridButton,
//    onClick: () -> Unit,
//) {
//    var isPressed by remember { mutableStateOf(false) }
//    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f)
//
//    Card(
//        modifier =
//            Modifier
//                .scale(scale)
//                .clickable(
//                    onClick = onClick,
//                    onClickLabel = item.title,
//                ),
//        shape = RoundedCornerShape(8.dp),
//        colors =
//            CardDefaults.cardColors(
//                containerColor = Color(0xFF2A2A2A),
//                contentColor = Color.White,
//            ),
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//        ) {
//            Icon(
//                imageVector = item.icon,
//                contentDescription = item.title,
//                tint = Color(0xFF00C853),
//                modifier = Modifier.size(32.dp),
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = item.title,
//                style =
//                    TextStyle(
//                        fontSize = 14.sp,
//                        fontWeight = FontWeight.Medium,
//                    ),
//            )
//        }
//    }
//}
