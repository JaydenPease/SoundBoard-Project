package com.mistershorr.soundboard

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(val note: String, val duration: Long, val volume: Float) : Parcelable
