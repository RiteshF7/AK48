package com.trex.laxmiemi.ui.actionresultscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.ui.actionresultscreen.ui.theme.LaxmiEmiTheme
import com.trex.laxmiemi.utils.GoogleMapUtils
import com.trex.rexnetwork.data.ActionMessageDTO
import com.trex.rexnetwork.data.Actions
import com.trex.rexnetwork.utils.getExtraData

class ContactResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionMessageDTO = intent.getExtraData<ActionMessageDTO>()
        val actionKey = actionMessageDTO.action.name
        val actionPayload = actionMessageDTO.payload.get(actionKey)

        enableEdgeToEdge()

        setContent {
            LaxmiEmiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        actionPayload?.let { payloadString ->
                            when {
                                actionKey == Actions.ACTION_GET_CONTACTS.name -> {
                                    val contactsMap = convertToMap(payloadString)
                                    ContactsList(contactsMap) {
                                        shareContacts(this@ContactResultActivity, contactsMap)
                                    }
                                }

                                actionKey == Actions.ACTION_GET_LOCATION.name -> {
                                    GoogleMapUtils.openGoogleMapUrl(
                                        this@ContactResultActivity,
                                        payloadString,
                                    )
                                    finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun convertToMap(payloadString: String): Map<String, String> =
        payloadString
            .split(", ")
            .associate { pair ->
                val (name, number) = pair.split(":")
                name.trim() to number.trim()
            }
}

@Composable
fun ContactsList(
    contacts: Map<String, String>,
    onShareContacts: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(contacts.entries.toList()) { entry ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            text = entry.key,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error,
                        )
                        Text(
                            text = entry.value,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 4.dp),
                        )
                    }
                }
            }
        }

        Button(
            onClick = onShareContacts,
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.8f),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier.padding(end = 8.dp),
                )
                Text(text = "Share Contacts")
            }
        }
    }
}

fun shareContacts(
    context: Context,
    contacts: Map<String, String>,
) {
    try {
        // Create a formatted string of contacts
        val contactsText =
            buildString {
                contacts.forEach { (name, number) ->
                    appendLine("Name: $name")
                    appendLine("Number: $number")
                    appendLine("-------------------")
                }
            }

        // Create intent to share text
        val sendIntent =
            Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Contacts List")
                putExtra(Intent.EXTRA_TEXT, contactsText)
            }

        // Create chooser
        val shareIntent = Intent.createChooser(sendIntent, "Share Contacts Via")
        context.startActivity(shareIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Error sharing contacts", Toast.LENGTH_SHORT).show()
    }
}
