package de.choesel.blechwiki;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CancelSynchronisationBroadcastReceiver extends BroadcastReceiver {
    public CancelSynchronisationBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SynchDataIntentService.stopSynchronistation();
        Toast.makeText(context,"Synchronistation angehalten",Toast.LENGTH_LONG);
    }
}
