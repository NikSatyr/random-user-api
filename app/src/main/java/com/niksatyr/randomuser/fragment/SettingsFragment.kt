package com.niksatyr.randomuser.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.niksatyr.randomuser.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }
}