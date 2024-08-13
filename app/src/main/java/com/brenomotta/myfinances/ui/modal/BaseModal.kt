package com.brenomotta.myfinances.ui.modal

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager

open class BaseModal(context: Context) : Dialog(context) {

    fun setWindowConfig() {
        val window = window
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = layoutParams
    }
}