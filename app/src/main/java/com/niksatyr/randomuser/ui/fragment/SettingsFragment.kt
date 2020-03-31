package com.niksatyr.randomuser.ui.fragment

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.niksatyr.randomuser.R
import com.niksatyr.randomuser.util.App

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
        val usersCountPreference: EditTextPreference = findPreference("pref_users_count")
            ?: throw IllegalStateException("Failed to find users count preference")
        val nightModePreference: SwitchPreference = findPreference("pref_night_mode")
            ?: throw IllegalStateException("Failed to find night mode preference")

        nightModePreference.setOnPreferenceChangeListener { _, newValue ->
            handleNightModeChange(newValue as Boolean)
            true
        }
    }

    private fun handleNightModeChange(nightModeEnabled: Boolean) {
        val app = activity?.application as App
        app.setNightMode(nightModeEnabled)
    }

}