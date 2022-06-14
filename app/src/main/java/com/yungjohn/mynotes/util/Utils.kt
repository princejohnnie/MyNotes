package com.yungjohn.mynotes.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Long): String{
    //@SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat("MMM d, yyyy HH:mm a", Locale.getDefault())

    val date = Date(timestamp)

    return formatter.format(date)

}


