package mx.dev1.pomodoro.core.crash

import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * Thin wrapper around [FirebaseCrashlytics] that provides named helpers for
 * setting user context, logging breadcrumbs, and reporting non-fatal errors.
 *
 * Crashlytics auto-captures fatal crashes — this class handles the rest.
 *
 * Usage:
 *   CrashlyticsManager.setUser(userId = "abc123", isPremium = true)
 *   CrashlyticsManager.log("User navigated to Settings")
 *   CrashlyticsManager.recordNonFatal(exception)
 */
object CrashlyticsManager {

    private val crashlytics get() = FirebaseCrashlytics.getInstance()

    // ── User context ───────────────────────────────────────────────────────

    /** Call after login / on app start if the user is already signed in. */
    fun setUser(userId: String, isPremium: Boolean) {
        crashlytics.setUserId(userId)
        crashlytics.setCustomKey("is_premium", isPremium)
    }

    /** Call on logout to remove user identity from future reports. */
    fun clearUser() {
        crashlytics.setUserId("")
        crashlytics.setCustomKey("is_premium", false)
    }

    // ── Custom keys ────────────────────────────────────────────────────────

    /** Attach the current timer mode so crash reports carry context. */
    fun setTimerMode(mode: String) {
        crashlytics.setCustomKey("timer_mode", mode)
    }

    /** Attach the active screen name to every crash report. */
    fun setCurrentScreen(screen: String) {
        crashlytics.setCustomKey("current_screen", screen)
    }

    fun setAppVersion(versionName: String, versionCode: Int) {
        crashlytics.setCustomKey("app_version_name", versionName)
        crashlytics.setCustomKey("app_version_code", versionCode)
    }

    // ── Breadcrumbs ────────────────────────────────────────────────────────

    /**
     * Log a plain text breadcrumb. Visible in the Crashlytics dashboard
     * under "Logs" for any crash report that follows.
     */
    fun log(message: String) {
        crashlytics.log(message)
    }

    // ── Non-fatal errors ───────────────────────────────────────────────────

    /**
     * Report a caught exception that didn't crash the app but is worth tracking
     * (e.g. a failed network call, a parse error, an unexpected state).
     */
    fun recordNonFatal(throwable: Throwable) {
        crashlytics.recordException(throwable)
    }

    /**
     * Report a non-fatal error from a plain message when no exception exists.
     */
    fun recordNonFatal(message: String) {
        crashlytics.recordException(RuntimeException(message))
    }

    // ── Collection toggle ──────────────────────────────────────────────────

    /**
     * Disable crash collection (e.g. when user opts out of analytics).
     * Persists across sessions — call [enable] to re-enable.
     */
    fun disable() {
        crashlytics.setCrashlyticsCollectionEnabled(false)
    }

    fun enable() {
        crashlytics.setCrashlyticsCollectionEnabled(true)
    }
}
