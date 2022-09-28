package com.sounge.soungeapp.response

import com.sounge.soungeapp.enums.RoleName

data class RoleSimple (
    var id: Long,
    var name: RoleName
)