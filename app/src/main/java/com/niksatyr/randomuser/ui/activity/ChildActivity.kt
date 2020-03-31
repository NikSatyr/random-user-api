package com.niksatyr.randomuser.ui.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.niksatyr.randomuser.R

abstract class ChildActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish() // We won't need this activity, hence may be killed
    }

    override fun onSupportNavigateUp(): Boolean {
        finish() // We won't need this activity, hence may be killed
        return super.onSupportNavigateUp()
    }
}