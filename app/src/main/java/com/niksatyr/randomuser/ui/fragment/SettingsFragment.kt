package com.niksatyr.randomuser.ui.fragment

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.niksatyr.randomuser.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
        val usersCountPreference: EditTextPreference = findPreference("pref_users_count") ?:
                throw IllegalStateException("Failed to find users count preference")
        // TODO check bounds
    }

}