package com.task.noteapp.util

import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object {
        fun getDate(): String
        {
            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val current = formatter.format(time)

            return current
        }
    }
}