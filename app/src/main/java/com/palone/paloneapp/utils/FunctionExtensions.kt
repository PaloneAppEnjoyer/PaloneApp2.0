package com.palone.paloneapp.utils

import android.graphics.Bitmap
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap

fun Int.between(from: Int, to: Int): Boolean {
    if (this in from..to)
        return true
    return false
}

@Composable
fun composableToBitmap(
    content: @Composable () -> Unit,
): () -> Bitmap {

    val context = LocalContext.current

    /**
     * ComposeView that would take composable as its content
     * Kept in remember so recomposition doesn't re-initialize it
     **/
    val composeView = remember { ComposeView(context) }
    /**
     * Callback function which could get latest image bitmap
     **/

    /** Use Native View inside Composable **/
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = {
            composeView.apply {
                setContent {
                    content.invoke()
                }
            }

        }
    )
    fun captureBitmap(): Bitmap {
        composeView.setContent {
            content.invoke()
        }
        return composeView.drawToBitmap()
    }

    /** returning callback to bitmap **/
    return ::captureBitmap
}

