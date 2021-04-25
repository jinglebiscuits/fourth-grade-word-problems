package com.completewordproblems.fourthgrade.customviews

import android.graphics.Path
import android.os.SystemClock

class MyPath(val type: Int) : Path() {
    val creationTime = SystemClock.uptimeMillis()
}