package com.example.sel.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.sel.R

class Utils {
    companion object{
        fun startActivityWithResultCode(
            beginActivity: ComponentActivity,
            finishActivity: ComponentActivity, resultCode: Int, bundle: Bundle?
        ) {
            val intentAcitivity = Intent(beginActivity, finishActivity::class.java)
            bundle?.let {
                intentAcitivity.putExtras(it)
            }
            beginActivity.startActivityForResult(intentAcitivity, resultCode)
            beginActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}