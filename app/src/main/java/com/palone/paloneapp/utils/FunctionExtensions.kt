package com.palone.paloneapp.utils

import android.graphics.Bitmap
import android.util.Log
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
    Log.i("ddd1", "${composeView.height}")
    /**
     * Callback function which could get latest image bitmap
     **/
//    val showDialog = remember {
//        mutableStateOf(false)
//    }
//    if (showDialog.value)
//        Dialog(onDismissRequest = {showDialog.value = false}) {
//            content.invoke()
//        }

    /** Use Native View inside Composable **/
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = {
            composeView.apply {
                setContent {
                    content.invoke()
                    Log.i("ddd4", "${composeView.height}")
                }
            }

        }
    )
    Log.i("ddd2", "${composeView.height}")
    fun captureBitmap(): Bitmap {
//        showDialog.value = true;
        composeView.setContent {
            content.invoke()
            Log.i("ddd5", "${composeView.height}")
        }
        return composeView.drawToBitmap()
    }

    /** returning callback to bitmap **/
    return ::captureBitmap
}

