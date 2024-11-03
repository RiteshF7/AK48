package com.trex.laxmiemi.ui.actionresultscreen

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trex.rexnetwork.utils.getExtraData
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseMap(
    val map: Map<String, String>,
) : Parcelable

class ResponseMapDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get map from intent or create sample data
        val dataMap = intent.getExtraData<ResponseMap>().map

        setContent {
            MaterialTheme {
                MapDisplayScreen(
                    dataMap = dataMap,
                    onFinish = { finish() },
                )
            }
        }
    }
}

@Composable
fun MapDisplayScreen(
    dataMap: Map<*, *>,
    onFinish: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Title
            Text(
                text = "Data Overview",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            // Map entries list
            LazyColumn(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(dataMap.entries.toList()) { entry ->
                    MapEntryCard(entry)
                }
            }

            // Finish button at bottom
            Button(
                onClick = onFinish,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(48.dp),
            ) {
                Text("Finish")
            }
        }
    }
}

@Composable
fun MapEntryCard(entry: Map.Entry<*, *>) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            // Key
            Text(
                text = entry.key.toString(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Value
            Text(
                text = entry.value.toString(),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

// Optional: Preview
@androidx.compose.ui.tooling.preview.Preview
@Composable
fun MapDisplayPreview() {
    val sampleData =
        mapOf(
            "User ID" to "12345",
            "Name" to "John Doe",
            "Email" to "john@example.com",
            "Status" to "Active",
        )

    MaterialTheme {
        MapDisplayScreen(
            dataMap = sampleData,
            onFinish = {},
        )
    }
}
