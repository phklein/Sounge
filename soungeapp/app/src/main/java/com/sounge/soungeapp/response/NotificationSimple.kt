package com.sounge.soungeapp.response

import com.sounge.soungeapp.enums.NotificationType

data class NotificationSimple (
    var id: Long,
    var text: String,
    var type: NotificationType,
    var hoursPast: Long,
    var sender: UserSimple
)