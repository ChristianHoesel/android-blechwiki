package de.choesel.blechwiki;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import de.choesel.blechwiki.model.BlaeserWikiFactory;
import de.choesel.blechwiki.model.Buch;
import de.choesel.blechwiki.model.Komponist;
import de.choesel.blechwiki.model.Titel;
import de.choesel.blechwiki.orm.BlechWikiRepository;
import de.choesel.blechwiki.orm.DatabaseHelper;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SynchDataIntentService extends IntentService {

    public static final int STOP_SYNCH = 20160529;

    private static boolean isStopped = false;


    public SynchDataIntentService() {
        super("SynchDataIntentService");
    }

    public static void startSynchonisation(Context context) {
        Intent intent = new Intent(context, SynchDataIntentService.class);
        context.startService(intent);
    }

    public static void stopSynchronistation() {
        isStopped = true;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_cached_black_24dp)
                            .setContentTitle("Bücher aktualisieren")
                            .setContentText("Aktualisieren der Bücher, Titel und Komponisten.");

            mBuilder.setOngoing(true);

            Intent stopIntent = new Intent();
            stopIntent.setAction("de.choesel.blechwiki.CancelSynchronisationBroadcastReceiver");
            PendingIntent broadcast = PendingIntent.getBroadcast(getApplicationContext(), STOP_SYNCH, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.addAction(R.drawable.ic_close_black_24dp, "Synchronisation abbrechen", broadcast);

            mBuilder.setProgress(15000, 0, false);
            mNotifyManager.notify(2709, mBuilder.build());

            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            BlechWikiRepository blechWikiRepository = new BlechWikiRepository(databaseHelper);

            int i = 0;
            for (Buch b : BlaeserWikiFactory.getBuecher()) {
                if(isStopped){
                    break;
                }
                blechWikiRepository.saveOrUpdateBuch(b);
                mBuilder.setProgress(15000, i++, false);
                mNotifyManager.notify(2709, mBuilder.build());
                if(isStopped){
                    break;
                }
            }


            for (Komponist k : BlaeserWikiFactory.getKomponisten()) {
                if(isStopped){
                    break;
                }
                blechWikiRepository.saveOrUpdateKomponist(k);
                mBuilder.setProgress(15000, i++, false);
                mNotifyManager.notify(2709, mBuilder.build());
                if(isStopped){
                    break;
                }
            }

            for (String s : BlaeserWikiFactory.getTitelNamen("")) {
                if(isStopped){
                    break;
                }
                for (Titel t : BlaeserWikiFactory.getFundStellen(s, databaseHelper)) {
                    if(isStopped){
                        break;
                    }
                    blechWikiRepository.saveOrUpdateTitel(t);
                    mBuilder.setProgress(15000, i++, false);
                    mNotifyManager.notify(2709, mBuilder.build());
                    if(isStopped){
                        break;
                    }
                }
                if(isStopped){
                    break;
                }
            }

            databaseHelper.close();

            mNotifyManager.cancel(2709);
            isStopped = false;
        }
    }


}
