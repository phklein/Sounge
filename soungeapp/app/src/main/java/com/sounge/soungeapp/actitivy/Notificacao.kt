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

class NotificacaoBuilder {
    var id: Long = 0
    var text: String = ""
    var type: NotificationType = NotificationType.MATCH
    val hoursPast: Long = 0
    val sender: UserSimple = UserSimple(id = 1, name = "aleatorio", "", isLeader = false)

    fun build(): Notificacao = Notificacao(id, text, type, hoursPast, sender)
}

fun notificacao(block: NotificacaoBuilder.() -> Unit): Notificacao =  NotificacaoBuilder().apply(block).build()

fun fakeNotificacao() = mutableListOf(
    notificacao {
        id = 1
        type = NotificationType.MATCH
    },
    notificacao {
        id = 2
        type = NotificationType.COMMENT
    },
    notificacao {
        id = 3
        type = NotificationType.LIKE
    }
)
