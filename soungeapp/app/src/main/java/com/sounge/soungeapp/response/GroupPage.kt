package com.sounge.soungeapp.response

data class GroupPage (
    var id: Long,
    var name: String,
    var description: String,
    var age: Int,
    var genres: List<GenreSimple>,
    var users: List<UserSimple>,
    var pictureUrl: String,
    var banner: String
)