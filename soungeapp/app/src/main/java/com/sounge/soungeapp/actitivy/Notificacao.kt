package com.sounge.soungeapp.actitivy

import com.sounge.soungeapp.enums.NotificationType
import com.sounge.soungeapp.response.UserSimple

data class Notificacao(
    val id: Long,
    val text: String,
    val type: NotificationType,
    val hoursPast: Long,
    val sender: UserSimple
)



