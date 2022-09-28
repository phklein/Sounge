package com.sounge.soungeapp.request

import com.sounge.soungeapp.enums.RoleName

data class UpdateRole(
    var toAdd: List<RoleName>,
    var toRemove: List<RoleName>
)
