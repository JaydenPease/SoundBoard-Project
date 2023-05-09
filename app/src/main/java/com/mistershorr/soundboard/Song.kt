package com.mistershorr.soundboard

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(var title: String, var notes: ArrayList<Note>): Parcelable
