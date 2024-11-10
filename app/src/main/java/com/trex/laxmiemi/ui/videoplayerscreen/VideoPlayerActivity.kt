package com.trex.laxmiemi.ui.videoplayerscreen

// VideoPlayerActivity.kt
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.trex.laxmiemi.utils.CommonConstants.EXAMPLE_VIDEO_URI

class VideoPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContent {
            val context = LocalContext.current

            val exoPlayer =
                remember {
                    ExoPlayer.Builder(context).build().apply {
                        val mediaItem = MediaItem.fromUri(EXAMPLE_VIDEO_URI)
                        setMediaItem(mediaItem)
                        prepare()
                    }
                }

            LaunchedEffect(Unit) {
                exoPlayer.play()
            }

            // Clean up player when activity is destroyed
            DisposableEffect(Unit) {
                onDispose {
                    exoPlayer.release()
                }
            }

            // ExoPlayer view
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                    }
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
