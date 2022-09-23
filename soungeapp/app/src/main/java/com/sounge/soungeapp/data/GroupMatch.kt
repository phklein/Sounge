package com.sounge.soungeapp.data

import com.sounge.soungeapp.enums.State

data class GroupMatch (
    var id: Long,
    var leaderId: Long,
    var name: String,
    var profilePic: String,
    var leaderState: State,
    var leaderCity: String,
    var description: String,
    var genres: List<GenreSimple>,
    var size: Int,
    var rolesFilled: List<RoleSimple>,
    var age: Int,
    var leaderDistance: Double,
    var relevance: Double,
    var leaderHasSignature: Boolean
)