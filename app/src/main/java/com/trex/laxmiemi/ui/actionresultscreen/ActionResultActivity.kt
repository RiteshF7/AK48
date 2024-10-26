package com.trex.laxmiemi.ui.actionresultscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trex.laxmiemi.ui.actionresultscreen.ui.theme.LaxmiEmiTheme
import com.trex.rexnetwork.data.ActionMessageDTOMapper
import com.trex.rexnetwork.data.Actions

class ActionResultActivity : ComponentActivity() {

    val sample = mapOf("ritesh" to "99999999","aman" to "01010101011","someone" to "8282838838")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionResultIntent =
            intent.getStringExtra(ACTION_RESULT_INTENT_KEY)

        val actionMessageDTO =
            actionResultIntent?.let {
                ActionMessageDTOMapper.fromJsonToDTO(it)
            }

        enableEdgeToEdge()

        setContent {
            LaxmiEmiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                        ContactsList(sample)

                        actionMessageDTO?.let { actionMessageDTO ->

                            when {
                                actionMessageDTO.action == Actions.ACTION_GET_CONTACTS -> {
                                    val contactPayload =
                                        actionMessageDTO.payload.get(Actions.ACTION_GET_CONTACTS.name)
                                    contactPayload?.let {
                                        val contactsMap =
                                            contactPayload
                                                .split(", ")
                                                .associate { pair ->
                                                    val (name, number) = pair.split(":")
                                                    name.trim() to number.trim()
                                                }
                                        ContactsList(contactsMap)
                                    }
                                }

                                actionMessageDTO.action == Actions.ACTION_GET_LOCATION -> {
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val ACTION_RESULT_INTENT_KEY = "action_result_intent_key"
    }
}

@Composable
fun ContactsList(contacts: Map<String, String>) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(contacts.entries.toList()) { entry ->
            Card(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
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
}
