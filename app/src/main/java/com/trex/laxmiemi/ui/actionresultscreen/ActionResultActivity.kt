package com.trex.laxmiemi.ui.actionresultscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.trex.laxmiemi.ui.actionresultscreen.ui.theme.LaxmiEmiTheme
import com.trex.rexnetwork.data.ActionMessageDTOMapper
import com.trex.rexnetwork.data.Actions

class ActionResultActivity : ComponentActivity() {
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
                        actionMessageDTO?.let { actionMessageDTO->
                            when{
                                actionMessageDTO.action==Actions.ACTION_GET_CONTACTS->{

                                }

                                actionMessageDTO.action==Actions.ACTION_GET_LOCATION->{

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
fun HandleContactsResult(contacts: List<String>){

}