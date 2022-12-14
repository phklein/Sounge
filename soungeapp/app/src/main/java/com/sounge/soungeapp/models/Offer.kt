package com.sounge.soungeapp.models

import androidx.annotation.DrawableRes

data class Offer(
    val plano: String,
    val tipoPlano: String,
    val valorPlano: String,
    @DrawableRes val logo: Int
)
