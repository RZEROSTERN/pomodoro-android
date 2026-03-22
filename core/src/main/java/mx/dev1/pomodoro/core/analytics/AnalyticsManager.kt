package mx.dev1.pomodoro.core.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

/**
 * Thin wrapper around [FirebaseAnalytics] that provides type-safe, named methods
 * for every event defined in [AnalyticsEvents].
 *
 * Usage:
 *   val analytics = AnalyticsManager.getInstance(context)
 *   analytics.logTimerStarted(mode = "focus", durationMinutes = 25)
 */
class AnalyticsManager private constructor(context: Context) {

    private val firebase = FirebaseAnalytics.getInstance(context.applicationContext)

    // ── Timer events ───────────────────────────────────────────────────────

    fun logTimerStarted(mode: String, durationMinutes: Int) {
        firebase.logEvent(AnalyticsEvents.TIMER_STARTED) {
            param(AnalyticsEvents.Params.TIMER_MODE, mode)
            param(AnalyticsEvents.Params.DURATION_MINUTES, durationMinutes.toLong())
        }
    }

    fun logTimerPaused(mode: String, remainingSeconds: Int) {
        firebase.logEvent(AnalyticsEvents.TIMER_PAUSED) {
            param(AnalyticsEvents.Params.TIMER_MODE, mode)
            param("remaining_seconds", remainingSeconds.toLong())
        }
    }

    fun logTimerResumed(mode: String) {
        firebase.logEvent(AnalyticsEvents.TIMER_RESUMED) {
            param(AnalyticsEvents.Params.TIMER_MODE, mode)
        }
    }

    fun logTimerCompleted(mode: String, sessionCount: Int) {
        firebase.logEvent(AnalyticsEvents.TIMER_COMPLETED) {
            param(AnalyticsEvents.Params.TIMER_MODE, mode)
            param(AnalyticsEvents.Params.SESSION_COUNT, sessionCount.toLong())
        }
    }

    fun logTimerStopped(mode: String) {
        firebase.logEvent(AnalyticsEvents.TIMER_STOPPED) {
            param(AnalyticsEvents.Params.TIMER_MODE, mode)
        }
    }

    fun logBreakStarted(breakType: String) {
        firebase.logEvent(AnalyticsEvents.BREAK_STARTED) {
            param(AnalyticsEvents.Params.TIMER_MODE, breakType)
        }
    }

    fun logBreakSkipped(breakType: String) {
        firebase.logEvent(AnalyticsEvents.BREAK_SKIPPED) {
            param(AnalyticsEvents.Params.TIMER_MODE, breakType)
        }
    }

    // ── Task events ────────────────────────────────────────────────────────

    fun logTaskCreated(hasTag: Boolean) {
        firebase.logEvent(AnalyticsEvents.TASK_CREATED) {
            param("has_tag", if (hasTag) 1L else 0L)
        }
    }

    fun logTaskCompleted(sessionCount: Int) {
        firebase.logEvent(AnalyticsEvents.TASK_COMPLETED) {
            param(AnalyticsEvents.Params.SESSION_COUNT, sessionCount.toLong())
        }
    }

    fun logTaskDeleted() {
        firebase.logEvent(AnalyticsEvents.TASK_DELETED, null)
    }

    // ── Auth events ────────────────────────────────────────────────────────

    fun logLoginAttempted(method: String) {
        firebase.logEvent(AnalyticsEvents.LOGIN_ATTEMPTED) {
            param(AnalyticsEvents.Params.AUTH_METHOD, method)
        }
    }

    fun logLoginSuccess(method: String) {
        firebase.logEvent(AnalyticsEvents.LOGIN_SUCCESS) {
            param(AnalyticsEvents.Params.AUTH_METHOD, method)
        }
    }

    fun logLoginFailed(method: String, reason: String) {
        firebase.logEvent(AnalyticsEvents.LOGIN_FAILED) {
            param(AnalyticsEvents.Params.AUTH_METHOD, method)
            param("reason", reason)
        }
    }

    fun logRegisterAttempted(method: String) {
        firebase.logEvent(AnalyticsEvents.REGISTER_ATTEMPTED) {
            param(AnalyticsEvents.Params.AUTH_METHOD, method)
        }
    }

    fun logRegisterSuccess(method: String) {
        firebase.logEvent(AnalyticsEvents.REGISTER_SUCCESS) {
            param(AnalyticsEvents.Params.AUTH_METHOD, method)
        }
    }

    fun logGoogleSignInTapped() {
        firebase.logEvent(AnalyticsEvents.GOOGLE_SIGNIN_TAPPED, null)
    }

    fun logAppleSignInTapped() {
        firebase.logEvent(AnalyticsEvents.APPLE_SIGNIN_TAPPED, null)
    }

    fun logLogout() {
        firebase.logEvent(AnalyticsEvents.LOGOUT, null)
    }

    // ── Premium events ─────────────────────────────────────────────────────

    fun logPremiumScreenViewed() {
        firebase.logEvent(AnalyticsEvents.PREMIUM_SCREEN_VIEWED, null)
    }

    fun logPremiumPlanSelected(plan: String) {
        firebase.logEvent(AnalyticsEvents.PREMIUM_PLAN_SELECTED) {
            param(AnalyticsEvents.Params.PLAN, plan)
        }
    }

    fun logPremiumUpgradeTapped(plan: String) {
        firebase.logEvent(AnalyticsEvents.PREMIUM_UPGRADE_TAPPED) {
            param(AnalyticsEvents.Params.PLAN, plan)
        }
    }

    // ── Data events ────────────────────────────────────────────────────────

    fun logDataExported(format: String) {
        firebase.logEvent(AnalyticsEvents.DATA_EXPORTED) {
            param(AnalyticsEvents.Params.FORMAT, format)
        }
    }

    fun logDataImported(format: String) {
        firebase.logEvent(AnalyticsEvents.DATA_IMPORTED) {
            param(AnalyticsEvents.Params.FORMAT, format)
        }
    }

    // ── User properties ────────────────────────────────────────────────────

    fun setUserId(userId: String?) {
        firebase.setUserId(userId)
    }

    fun setIsPremium(isPremium: Boolean) {
        firebase.setUserProperty(AnalyticsEvents.Params.IS_PREMIUM, isPremium.toString())
    }

    // ── Singleton ──────────────────────────────────────────────────────────

    companion object {
        @Volatile private var instance: AnalyticsManager? = null

        fun getInstance(context: Context): AnalyticsManager =
            instance ?: synchronized(this) {
                instance ?: AnalyticsManager(context).also { instance = it }
            }
    }
}
