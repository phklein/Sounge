package com.sounge.soungeapp.response

data class UserSimple (
    var id: Long,
    var name: String,
    var profilePic: String,
    var isLeader: Boolean
)