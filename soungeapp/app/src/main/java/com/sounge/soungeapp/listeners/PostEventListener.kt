package com.sounge.soungeapp.listeners

interface PostEventListener {
    fun onLike(position: Int)
    fun onUnlike(position: Int)
}