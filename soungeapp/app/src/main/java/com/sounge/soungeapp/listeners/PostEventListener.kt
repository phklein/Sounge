package com.sounge.soungeapp.listeners

import com.sounge.soungeapp.data.PostSimple

interface PostEventListener : CommentEventListener {
    fun onClickComment(post: PostSimple, position: Int)
}