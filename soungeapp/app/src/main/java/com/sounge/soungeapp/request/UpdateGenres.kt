package com.sounge.soungeapp.request

import com.sounge.soungeapp.enums.GenreName
import com.sounge.soungeapp.enums.RoleName

data class UpdateGenres(
    var toAdd: List<GenreName>,
    var toRemove: List<GenreName>
)
