package com.sounge.soungeapp.data

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
    var likedGenres: List<GenreSimple>,
    var roles: List<RoleSimple>,
    var age: Int,
    var isViewerProfile: Boolean,
    var postList: List<PostSimple>
)