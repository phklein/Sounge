package com.sounge.soungeapp.data

import com.sounge.soungeapp.enums.GenreName
import com.sounge.soungeapp.enums.RoleName
import com.sounge.soungeapp.enums.Sex
import com.sounge.soungeapp.enums.State
import java.time.LocalDate

data class SaveUsers(
    val email: String,
    val password: String,
    val name: String,
    val sex: Sex,
    val description: String,
    val birthDate: LocalDate,
    val state: State,
    val city: String,
    val likedGenres: List<GenreName>,
    val roles: List<RoleName>,
    val phone: String
)
