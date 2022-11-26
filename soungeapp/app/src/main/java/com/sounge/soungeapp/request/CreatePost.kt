package com.sounge.soungeapp.request

data class CreatePost(
    var userId: Long?,
    var groupId: Long?,
    var text: String,
    var mediaUrl: String
)
