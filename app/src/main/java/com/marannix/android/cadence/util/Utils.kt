package com.marannix.android.cadence.util

import android.os.Handler

/**
 * Just using this to delay the lottie error animation due to loading too fast,
 * which causes the loading animation to disappear too quickly making the UI look weird
 */
class Utils {
   companion object {
       fun delayFunction(function: () -> Unit, delay: Long) {
           Handler().postDelayed(function, delay)
       }
   }
}