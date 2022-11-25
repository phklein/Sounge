package com.sounge.soungeapp.data

import android.os.Parcelable
import com.sounge.soungeapp.enums.GenreName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreSimple (
    var id: Long,
    var genreName: GenreName
) : Parcelable