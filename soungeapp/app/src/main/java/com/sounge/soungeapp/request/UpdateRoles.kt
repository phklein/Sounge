package com.sounge.soungeapp.request

import com.sounge.soungeapp.enums.RoleName

data class UpdateRoles(
    var toAdd: List<RoleName>,
    var toRemove: List<RoleName>
)
