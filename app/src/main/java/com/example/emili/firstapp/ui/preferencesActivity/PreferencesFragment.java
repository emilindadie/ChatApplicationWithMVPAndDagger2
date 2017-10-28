package com.example.emili.firstapp.ui.preferencesActivity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.emili.firstapp.R;

/**
 * Created by emili on 26/10/2017.
 */

public class PreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstancestate){
        super.onCreate(savedInstancestate);

        addPreferencesFromResource(R.xml.preferences);
    }
}
