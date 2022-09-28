package com.sounge.soungeapp.response

import com.sounge.soungeapp.enums.Sex
import com.sounge.soungeapp.enums.SkillLevel
import com.sounge.soungeapp.enums.State

data class UserMatch (
    var id: Long,
    var name: String,
    var profilePic: String,
    var isLeader: Boolean,
    var sex: Sex,
    var state: State,
    var city: String,
    var description: String,
    var isOnline: Boolean,
    var skillLevel: SkillLevel,
    var group: GroupSimple,
    var likedGenres: List<GenreSimple>,
    var roles: List<RoleSimple>,
    var age: Int,
    var distance: Double,
    var relevance: Double,
    var hasSignature: Boolean,
    var phone: String
)