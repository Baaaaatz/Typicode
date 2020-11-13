package com.batzalcancia.core.helpers

import android.content.Context

fun Int.dp(context: Context): Int = (this / context.resources.displayMetrics.density).toInt()
fun Int.px(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()