package com.example.pixiti.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog

/*
 * Create an alert dialog
 */
fun Context.showAlertDialog(
    title: String,
    message: String,
    isCancellable: Boolean = true,
    negativeButtonText: String,
    positiveButtonText: String,
    negativeButtonListener: (() -> Unit)? = null,
    positivebuttonListener: () -> Unit
) =
    run {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle(title)
            setMessage(message)
        }
        builder.setNegativeButton(negativeButtonText) { dialog, _ ->
            negativeButtonListener?.invoke() ?: run {
                dialog.cancel()
            }
        }
        builder.setPositiveButton(positiveButtonText) { dialog, _ ->
            positivebuttonListener.invoke()
            dialog.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(isCancellable)
        alertDialog.show()
    }

/*
 * Close the keyboard
 */
fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}