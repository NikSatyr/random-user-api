package com.niksatyr.randomuser.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.niksatyr.randomuser.R

class SettingsActivity : AppCompatActivity(R.layout.activity_settings) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
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