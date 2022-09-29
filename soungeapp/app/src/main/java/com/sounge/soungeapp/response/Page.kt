package com.sounge.soungeapp.response

class Page<T>() {
    private lateinit var content: List<T>
    private var last: Boolean = false
    private var number: Int = 0
    private var empty: Boolean = false
}
