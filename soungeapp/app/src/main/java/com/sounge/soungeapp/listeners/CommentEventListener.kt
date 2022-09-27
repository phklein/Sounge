package com.sounge.soungeapp.listeners

interface CommentEventListener {
    fun onUnlike(position: Int)
    fun onLike(position: Int)
}