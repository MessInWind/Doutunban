package com.xyh.dtb.singleton

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.xyh.dtb.R
import kotlin.reflect.KType

@Suppress("ALL")
object GlobalMessage { // 全局消息组件
    private var context: Context? = null
    private val handler = Handler(Looper.getMainLooper())

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun showMessage(message: String) {
        context?.let {
            handler.post {
                val inflater = LayoutInflater.from(it)
                val layout = inflater.inflate(R.layout.toast_custom, null)
                val textView: TextView = layout.findViewById(R.id.toast_message)
                textView.text = message

                val toast = Toast(it)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()
            }
        }
    }
}

//    fun showMessage(message: String) {
//        context?.let {
//            handler.post {
//                Toast.makeText(it, message, Toast.LENGTH_LONG).show()
//            }
//        }
//    }
