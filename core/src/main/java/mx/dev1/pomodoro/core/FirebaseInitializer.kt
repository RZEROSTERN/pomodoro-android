package mx.dev1.pomodoro.core

import android.content.Context
import com.google.firebase.FirebaseApp

object FirebaseInitializer {
    fun init(context: Context) {
        FirebaseApp.initializeApp(context)
    }
}
