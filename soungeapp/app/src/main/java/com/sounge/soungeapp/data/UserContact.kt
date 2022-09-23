package com.sounge.soungeapp.data

data class UserContact (
    var id: Long,
    var name: String,
    var profilePic: String,
    var leader: Boolean,
    var phoneNumber: String
)