package com.example.mymovie.utils

import android.net.Uri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.dash.DefaultDashChunkSource
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.smoothstreaming.DefaultSsChunkSource
import androidx.media3.exoplayer.smoothstreaming.SsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder

class Utils {
    companion object {
        /**
         * if model contains <strong>url</strong>, will throw illegalArgumentException.
         *
         * Required to encode with utf-8 after parsed to jsonString.
         *
         * Ref: https://stackoverflow.com/questions/67121433/how-to-pass-object-in-navigation-in-jetpack-compose
         */
        private fun String.urlEncode(): String = URLEncoder.encode(this, "utf-8")

        fun String.urlDecode(): String = URLDecoder.decode(this, "utf-8")

        fun modelToJson(model: Any, isHasUrl: Boolean = false): String =
            if (isHasUrl) Gson().toJson(model, model.javaClass).urlEncode()
            else Gson().toJson(model, model.javaClass)

        inline fun <reified T> modelFromJson(model: String, isHasUrl: Boolean = false): T =
            if (isHasUrl) Gson().fromJson(model.urlDecode(), T::class.java)
            else Gson().fromJson(model, T::class.java)

        @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
        fun buildMediaSource(
            uri: Uri
        ): MediaSource {
            @C.ContentType val type = Util.inferContentType(uri)
            val mediaItem = MediaItem.Builder().setUri(uri).build()
            val mediaDataSourceFactory = DefaultHttpDataSource.Factory()
            return when (type) {
                C.CONTENT_TYPE_DASH -> DashMediaSource.Factory(
                    DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                    DefaultHttpDataSource.Factory()
                ).createMediaSource(mediaItem)
                C.CONTENT_TYPE_SS -> SsMediaSource.Factory(
                    DefaultSsChunkSource.Factory(mediaDataSourceFactory),
                    DefaultHttpDataSource.Factory()
                )
                    .createMediaSource(mediaItem)
                C.CONTENT_TYPE_HLS -> HlsMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(mediaItem)
                C.CONTENT_TYPE_OTHER, C.CONTENT_TYPE_RTSP -> ProgressiveMediaSource.Factory(
                    mediaDataSourceFactory
                ).createMediaSource(mediaItem)
                else -> {
                    throw IllegalStateException("Unsupported type: $type")
                }
            }
        }
    }
}