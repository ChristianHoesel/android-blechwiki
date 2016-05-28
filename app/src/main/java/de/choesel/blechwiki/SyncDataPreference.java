package de.choesel.blechwiki;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

/**
 * Created by Christian on 15.05.2016.
 */
public class SyncDataPreference extends Preference {

    public SyncDataPreference(Context context,
                              AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onClick() {
        super.onClick();
        SynchDataIntentService.startSynchonisation(getContext());
    }
}
