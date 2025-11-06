package util

import android.app.Activity
import android.content.Context
import android.content.Intent

class Util {
    companion object{
        fun openActivity(context: Context, objClassActivity: Class<*>){
            val objIntent = Intent(context,objClassActivity)
            context.startActivity(objIntent)
        }
    }
}