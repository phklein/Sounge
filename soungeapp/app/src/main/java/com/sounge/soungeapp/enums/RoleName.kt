package com.sounge.soungeapp.enums

import com.sounge.soungeapp.R

enum class RoleName(val s: String, val icon: Int) {
    GUITARIST("Violonista", R.drawable.ic_guitarist),
    EGUITARIST("Guitarrista", R.drawable.ic_eguitarist),
    VIOLINIST("Violinista", R.drawable.ic_violinist),
    BASSPLAYER("Baixista", R.drawable.ic_bass_player),
    UKULELEPLAYER("Ukulelista", R.drawable.ic_ukulele_player),
    DRUMMER("Baterista", R.drawable.ic_drummer),
    TAMBOURINEPLAYER("Pandeirista", R.drawable.ic_tambourine_player),
    PIANIST("Pianista", R.drawable.ic_pianist),
    EKEYBOARDPLAYER("Tecladista", R.drawable.ic_ekeyboard_player),
    ACCORDIONIST("Sanfoneiro(a)", R.drawable.ic_accordionist),
    CORNETPLAYER("Corneteiro(a)", R.drawable.ic_cornet_player),
    TROMBONIST("Trombonista", R.drawable.ic_trombonist),
    SAXOPHONIST("Saxofonista", R.drawable.ic_saxophonist),
    FLUTIST("Flautista", R.drawable.ic_flutist),
    DJ("DJ", R.drawable.ic_dj),
    PRODUCER("Produtor(a)", R.drawable.ic_producer),
    VOCALIST("Vocalista", R.drawable.ic_vocalist),
    OTHERS("Outros", R.drawable.ic_others)
}