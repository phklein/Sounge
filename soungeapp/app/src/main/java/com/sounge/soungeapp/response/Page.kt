package com.sounge.soungeapp.response

data class Page<T>(
    var content: MutableList<T>,
    var last: Boolean,
    var number: Int,
    var isEmpty: Boolean
)