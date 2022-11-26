package com.sounge.soungeapp.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroupSimple (
    var id: Long,
    var name: String,
    var profilePic: String
) : Parcelable