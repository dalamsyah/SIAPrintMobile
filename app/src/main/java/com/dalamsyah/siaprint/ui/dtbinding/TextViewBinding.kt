package com.dalamsyah.siaprint.ui.dtbinding

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dalamsyah.siaprint.models.Siaprint

class TextViewBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("bindTextViewColor")
        fun bindTextViewColor(textView: TextView, transaction_status: String?) {
            when(transaction_status){

                //menunggu pembayaran
                Siaprint.WAITING_PAYMENT ->
                    textView.setBackgroundColor(Color.RED)

                //sudah dibayar
                Siaprint.PAYMENET_DONE ->
                    textView.setBackgroundColor(Color.GREEN)

                //proses printing
                Siaprint.PRINTING ->
                    textView.setBackgroundColor(Color.YELLOW)

                //Siap Delivery / Pickup
                Siaprint.SIAP_DELVIERY ->
                    textView.setBackgroundColor(Color.BLUE)

            }

            textView.setTextColor(Color.WHITE)
        }

    }

}