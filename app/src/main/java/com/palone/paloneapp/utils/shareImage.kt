package com.palone.paloneapp.utils

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File

fun shareAImage(context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
    val photoFile = File(context.filesDir, "share.png")
    intent.putExtra(
        Intent.EXTRA_STREAM, FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            photoFile
        )
    )
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    intent.type = "image/*"
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
    context.startActivity(Intent.createChooser(intent, "Share Via"))
}