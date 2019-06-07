package com.example.hatansaad.popularmoviesapp;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings);

            Preference sortByPreference = findPreference(getString(R.string.sort_by_key));
            setUpPreference(sortByPreference);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            ListPreference listPreference = (ListPreference) preference;
            String newSortBy = newValue.toString();

            int index = listPreference.findIndexOfValue(newSortBy);

            if (index >= 0) {
                CharSequence[] entries = listPreference.getEntries();
                preference.setSummary(entries[index]);
            }
            return true;
        }


        private void setUpPreference(Preference preference) {

            SharedPreferences sharedPreferences = preference.getSharedPreferences();
            String selectedSortValue = sharedPreferences.getString(preference.getKey(),"test");
            preference.setOnPreferenceChangeListener(this);
            onPreferenceChange(preference, selectedSortValue);
        }

    }
}
