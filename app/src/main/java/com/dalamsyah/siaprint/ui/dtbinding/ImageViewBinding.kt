package com.dalamsyah.siaprint.ui.dtbinding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dalamsyah.siaprint.R

class ImageViewBinding {
    companion object {

        @JvmStatic
        @BindingAdapter("bindImageViewSrc")
        fun bindImageViewSrc(imageView: ImageView, selected: Boolean) {
            if (selected){
                imageView.setBackgroundResource(R.drawable.ic_baseline_check_circle_24_green)
            } else {
                imageView.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_24_grey)
            }
        }

    }
}