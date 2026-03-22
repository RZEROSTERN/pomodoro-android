package mx.dev1.pomodoro

import android.app.Application
import com.github.mikephil.charting.BuildConfig
import mx.dev1.pomodoro.core.FirebaseInitializer
import mx.dev1.pomodoro.core.crash.CrashlyticsManager

class PomodoroApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialise Firebase (Analytics and Crashlytics auto-start after this)
        FirebaseInitializer.init(this)

        // Attach static context that will appear in every crash report
        CrashlyticsManager.setAppVersion(
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE
        )
    }
}
