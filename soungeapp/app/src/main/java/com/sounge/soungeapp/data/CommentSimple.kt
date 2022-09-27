package com.sounge.soungeapp.data

data class CommentSimple (
    var id: Long,
    var text: String,
    var mediaUrl: String,
    var hoursPast: Long,
    var user: UserSimple,
    var likeCount: Int,
    var hasLiked: Boolean
)