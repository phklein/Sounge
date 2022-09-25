package com.sounge.soungeapp.utils

import android.Manifest
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.PopupWindow
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.PopupFullscreenMediaBinding


object ImageUtils {
    fun popupImage(drawable: Drawable, location: View) {
        val popupBinding =
            PopupFullscreenMediaBinding.inflate(LayoutInflater.from(location.context))
        val imageView = popupBinding.ivFullscreenImage
        imageView.setImageDrawable(drawable)

        val closeButton = popupBinding.ivCloseButton

        val builder = PopupWindow(
            popupBinding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )
        builder.animationStyle = R.style.DialogAnimation
        builder.showAtLocation(location, Gravity.CENTER, 0, 0)

        closeButton.setOnClickListener {
            builder.dismiss()
        }
    }
}