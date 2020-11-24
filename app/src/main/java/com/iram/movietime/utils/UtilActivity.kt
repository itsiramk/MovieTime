package com.iram.movietime.utils

import android.view.View
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.layout_moviedetails.*

class UtilActivity {
    companion object{
        fun showProgress(pBar: ProgressBar, status: Boolean) {
            if (status) {
                pBar.visibility = View.VISIBLE
            } else {
                pBar.visibility = View.GONE
            }
        }
    }
}