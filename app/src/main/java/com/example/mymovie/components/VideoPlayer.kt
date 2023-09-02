package com.example.mymovie.components

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.mymovie.utils.Utils
import com.example.mymovie.utils.rememberLifecycleEvent

/**
 * currently not working for youtube url
 *
 * example video url: https://gist.github.com/jsturgis/3b19447b304616f18657
 *
 * todo: look for solution in future
 * */
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun ExoPlayer(videoUri: Uri, height: Dp) {
    val context = LocalContext.current
    val lifecycleEvent = rememberLifecycleEvent()
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val source = Utils.buildMediaSource(videoUri)
                setMediaSource(source)
                playWhenReady = true
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                repeatMode = Player.REPEAT_MODE_ONE
                prepare()
            }
    }
    var isPlaying by remember { mutableStateOf(exoPlayer.playWhenReady) }
    var isMuted by remember { mutableStateOf(exoPlayer.volume == 0F) }
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_PAUSE) {
            if (isPlaying) exoPlayer.pause()
            isPlaying = false
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .aspectRatio(16 / 9f)
        ) {
            AndroidView(factory = {
                PlayerView(context).apply {
                    hideController()
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            })
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 48.dp, end = 48.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .clickable {
                        if (isPlaying) exoPlayer.pause()
                        else exoPlayer.play()
                        isPlaying = !isPlaying
                    },
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(48.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .clickable {
                        if (isMuted) {
                            exoPlayer.volume = 1F
                        } else {
                            exoPlayer.volume = 0F
                        }
                        isMuted = !isMuted
                    },
            ) {
                Icon(
                    imageVector = if (isMuted) Icons.Filled.VolumeOff else Icons.Filled.VolumeUp,
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }
        }
    }
}