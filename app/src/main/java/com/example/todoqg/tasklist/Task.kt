package com.example.todoqg.tasklist

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Task(var id: String,var title: String, var description: String = " no description") :
    Parcelable