package com.sounge.soungeapp.data

data class UserLogin (
    var id: Long,
    var name: String,
    var profilePic: String,
    var leader: Boolean,
    var newNotifications: Long,
    var newMatches: Long
)