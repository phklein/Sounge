package com.sounge.soungeapp.data

import android.os.Parcelable
import com.sounge.soungeapp.enums.RoleName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoleSimple (
    var id: Long,
    var roleName: RoleName
) : Parcelable