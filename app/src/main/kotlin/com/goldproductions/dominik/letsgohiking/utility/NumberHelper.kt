package com.goldproductions.dominik.letsgohiking.utility

fun getFormattedDistanceString(distance: Float?): String {
    if (distance == null) {
        return ""
    }
    if (distance < 1000f) {
        return "${distance.toInt()} m"
    }
    val kilometers: Int = (distance / 1000).toInt()
    val meters: Int = ((distance % 1000) / 100).toInt()
    return "$kilometers.$meters km"
}