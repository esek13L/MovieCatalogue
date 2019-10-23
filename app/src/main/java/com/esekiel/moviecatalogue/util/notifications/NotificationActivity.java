package com.esekiel.moviecatalogue.util.notifications;

import android.os.Bundle;
import android.widget.Toast;

import com.esekiel.moviecatalogue.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

        private NotificationReceiver receiver;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.notifications, rootKey);


        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            receiver = new NotificationReceiver(getContext());
            SwitchPreferenceCompat switchReminder = findPreference(getString(R.string.key_daily_reminder));
            if (switchReminder != null) {
                switchReminder.setOnPreferenceChangeListener(this);
            }

            SwitchPreferenceCompat switchToday = findPreference(getString(R.string.key_release_reminder));
            if (switchToday != null) {
                switchToday.setOnPreferenceChangeListener(this);
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean value = (boolean) newValue;

            if (key.equals(getString(R.string.key_daily_reminder))) {
                if (value) {
                    //add value
                    receiver.setDailyReminder();

                } else {
                    receiver.cancelDailyReminder(getContext());
                    Toast.makeText(getActivity(), "Daily Alarm Deactivated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (value) {
                    receiver.setReleaseTodayReminder();
                } else {
                    receiver.cancelReleaseToday(getContext());
                    Toast.makeText(getActivity(), "Release Alarm Deactivated", Toast.LENGTH_SHORT).show();

                }
            }


            return true;
        }
    }
}


