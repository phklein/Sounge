package com.sounge.soungeapp.response

import com.sounge.soungeapp.enums.SkillLevel

data class UserPage (
    var id: Long,
    var name: String,
    var profilePic: String,
    var banner: String,
    var isLeader: Boolean,
    var description: String,
    var isOnline: Boolean,
    var skillLevel: SkillLevel,
    var group: GroupSimple,
    var likedGenres: MutableList<GenreSimple>,
    var roles: MutableList<RoleSimple>,
    var age: Int,
    var viewerProfile: Boolean,
    var postList: Page<PostSimple>
)