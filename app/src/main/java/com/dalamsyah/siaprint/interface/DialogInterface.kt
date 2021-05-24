package com.dalamsyah.siaprint.`interface`


interface DialogInterface {
    fun onClickOK()
}

class CustomDialog(var listener: DialogInterface){

    private val clickEvent = {}

    fun setOkClickListener(
        click: () -> Unit = clickEvent
    ): DialogInterface {
        return object : DialogInterface {
            override fun onClickOK() {
                click()
            }
        }
    }

}

