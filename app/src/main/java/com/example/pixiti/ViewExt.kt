package com.example.pixiti

import android.view.View

/*
 * Sets View visibility to Visible
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/*
 * Sets View visibility to Invisible
 */
fun View.hide() {
    this.visibility = View.INVISIBLE
}

/*
 * Sets View visibility to Gone
 */
fun View.dismiss() {
    this.visibility = View.GONE
}

/*
 * Sets View visibility due to condition
 */
fun View.showIf(statement: Boolean?) {
    this.visibility = if (statement != null && statement) View.VISIBLE else View.GONE
}