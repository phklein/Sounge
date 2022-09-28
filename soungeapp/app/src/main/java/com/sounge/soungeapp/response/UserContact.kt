package com.sounge.soungeapp.response

data class UserContact (
    var id: Long,
    var name: String,
    var profilePic: String,
    var leader: Boolean,
    var phoneNumber: String
)