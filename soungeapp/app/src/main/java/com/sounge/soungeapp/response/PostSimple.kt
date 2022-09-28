package com.sounge.soungeapp.response

data class PostSimple(
    var id: Long,
    var text: String,
    var mediaUrl: String,
    var hoursPast: Long,
    var user: UserSimple?,
    var group: GroupSimple?,
    var likeCount: Int,
    var commentCount: Int,
    var hasLiked: Boolean
)