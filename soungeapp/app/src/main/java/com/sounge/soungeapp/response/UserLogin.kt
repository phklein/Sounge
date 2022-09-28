package com.sounge.soungeapp.response

data class UserLogin (
    var id: Long,
    var name: String,
    var profilePic: String,
    var leader: Boolean,
    var newNotifications: Long,
    var newMatches: Long
)