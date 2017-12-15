package c4n.net.cataloguemovieuiux.Activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import c4n.net.cataloguemovieuiux.GcmNetwork.SchedulerTask;
import c4n.net.cataloguemovieuiux.R;
import c4n.net.cataloguemovieuiux.Util.AlarmReceiver;

import java.util.List;


import static android.R.id.content;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity {
    private AlarmReceiver alarmReceiver = new AlarmReceiver();
    private SchedulerTask schedulerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(content, new MyPreferenceFragment()).commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener{

        @BindString(R.string.key_reminder_daily)
        String reminderDaily;

        @BindString(R.string.key_reminder_upcoming)
        String reminderUpcoming;

        @BindString(R.string.key_setting_locale)
        String settingLocale;

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);

            ButterKnife.bind(this, getActivity());

            findPreference(reminderDaily).setOnPreferenceChangeListener(this);
            findPreference(reminderUpcoming).setOnPreferenceChangeListener(this);
            findPreference(settingLocale).setOnPreferenceChangeListener(this);

            schedulerTask = new SchedulerTask(getActivity());
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String key = preference.getKey();
            boolean isOn = (boolean) o;

            if (key.equals(reminderDaily)) {
                if (isOn) {
                    alarmReceiver.setRepeatingAlarm(getActivity(), alarmReceiver.TYPE_REPEATING, "08:00", getString(R.string.label_alarm_daily_reminder));
                } else {
                    alarmReceiver.cancelAlarm(getActivity(), alarmReceiver.TYPE_REPEATING);
                }
                Toast.makeText(SettingsActivity.this, getString(R.string.label_daily_reminder) + " " + (isOn ? getString(R.string.label_activated) : getString(R.string.label_deactivated)), Toast.LENGTH_SHORT).show();
                return true;
            }

            if (key.equals(reminderUpcoming)) {
                if (isOn) {
                    schedulerTask.createPeriodicTask();
                } else schedulerTask.cancelPeriodicTask();

                Toast.makeText(SettingsActivity.this, getString(R.string.label_upcoming_reminder) + " " + (isOn ? getString(R.string.label_activated) : getString(R.string.label_deactivated)), Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            String key = preference.getKey();

            if (key.equals(settingLocale)){
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
            }
            return false;
        }
    }

}