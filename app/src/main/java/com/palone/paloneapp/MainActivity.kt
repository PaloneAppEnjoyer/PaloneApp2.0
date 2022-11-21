package com.palone.paloneapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.palone.paloneapp.ui.PaloneApp
import com.palone.paloneapp.ui.SubstitutionsViewModel
import com.palone.paloneapp.ui.TimetableViewModel
import com.palone.paloneapp.ui.theme.PaloneAppTheme

class MainActivity : ComponentActivity() {
    private val CHANNELID = "com.palone.paloneapp.channelID"
    private val CHANNELNAME = "Main notification channel"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val substitutionsViewModel by viewModels<SubstitutionsViewModel>()
        val timetableViewModel by viewModels<TimetableViewModel>()
        createNotificationChannel()
        Firebase.messaging.subscribeToTopic("mainTopic")
            .addOnCompleteListener { task ->
                var msg = "subscribed"
                if (!task.isSuccessful) {
                    msg = "subscription failed"
                }
                Log.i("subscription status", msg)
            }

        val colors = darkColors(
            primary = Color(pref.getLong("primary", 0xFF6F6F6F)),
            primaryVariant = Color(pref.getLong("primaryVariant", 0xFFFCFCFC)),//subs elements
            secondary = Color(pref.getLong("secondary", 0xFF9B2720)),
            background = Color(pref.getLong("background", 0xFFF9F1EF)),//Light Pink
            surface = Color(pref.getLong("surface", 0xFF9B2720)), //TEXT COLOR
            onBackground = Color(pref.getLong("onBackground", 0xFFF9F1EF))
        )
        /*val colors = darkColors(
            primary = Color(0xFF6F6F6F),
            primaryVariant = Color(0xFFFCFCFC),//subs elements
            secondary = Color(0xFF9B2720),
            background = Color(0xFFF9F1EF),//Light Pink
            surface = Color(0xFF9B2720), //TEXT COLOR
            onBackground = Color(0xFFF9F1EF)
        )*/


        setContent {
            PaloneAppTheme(colors = colors) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PaloneApp(
                        substitutionsViewModel = substitutionsViewModel,
                        timetableViewModel = timetableViewModel
                    )
                }
            }
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.i("token", token)
        })

    }

    // Declare the launcher at the top of your Activity/Fragment:
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNELID,
                CHANNELNAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.Cyan.value.toInt()
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}

