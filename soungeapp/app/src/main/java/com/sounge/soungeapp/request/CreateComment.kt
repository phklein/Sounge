package com.sounge.soungeapp.request

data class CreateComment(
    var userId: Long,
    var text: String,
    var mediaUrl: String
)
