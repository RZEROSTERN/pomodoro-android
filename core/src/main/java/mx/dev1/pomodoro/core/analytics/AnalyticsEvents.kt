package mx.dev1.pomodoro.core.analytics

/**
 * Central registry of all Firebase Analytics event names and parameter keys.
 * Use these constants everywhere instead of raw strings to avoid typos.
 */
object AnalyticsEvents {

    // ── Timer ──────────────────────────────────────────────────────────────
    const val TIMER_STARTED         = "timer_started"
    const val TIMER_PAUSED          = "timer_paused"
    const val TIMER_RESUMED         = "timer_resumed"
    const val TIMER_COMPLETED       = "timer_completed"
    const val TIMER_STOPPED         = "timer_stopped"
    const val BREAK_STARTED         = "break_started"
    const val BREAK_SKIPPED         = "break_skipped"

    // ── Tasks ──────────────────────────────────────────────────────────────
    const val TASK_CREATED          = "task_created"
    const val TASK_COMPLETED        = "task_completed"
    const val TASK_DELETED          = "task_deleted"

    // ── Auth ───────────────────────────────────────────────────────────────
    const val LOGIN_ATTEMPTED       = "login_attempted"
    const val LOGIN_SUCCESS         = "login_success"
    const val LOGIN_FAILED          = "login_failed"
    const val REGISTER_ATTEMPTED    = "register_attempted"
    const val REGISTER_SUCCESS      = "register_success"
    const val GOOGLE_SIGNIN_TAPPED  = "google_signin_tapped"
    const val APPLE_SIGNIN_TAPPED   = "apple_signin_tapped"
    const val LOGOUT                = "logout"

    // ── Premium ────────────────────────────────────────────────────────────
    const val PREMIUM_SCREEN_VIEWED = "premium_screen_viewed"
    const val PREMIUM_PLAN_SELECTED = "premium_plan_selected"
    const val PREMIUM_UPGRADE_TAPPED = "premium_upgrade_tapped"

    // ── Data ───────────────────────────────────────────────────────────────
    const val DATA_EXPORTED         = "data_exported"
    const val DATA_IMPORTED         = "data_imported"

    // ── Parameter keys ─────────────────────────────────────────────────────
    object Params {
        const val TIMER_MODE        = "timer_mode"       // "focus" | "short_break" | "long_break"
        const val DURATION_MINUTES  = "duration_minutes"
        const val SESSION_COUNT     = "session_count"
        const val TASK_NAME         = "task_name"
        const val TAG               = "tag"
        const val AUTH_METHOD       = "auth_method"      // "email" | "google" | "apple"
        const val PLAN              = "plan"             // "monthly" | "annual" | "lifetime"
        const val FORMAT            = "format"           // "csv" | "json"
        const val IS_PREMIUM        = "is_premium"
    }
}
