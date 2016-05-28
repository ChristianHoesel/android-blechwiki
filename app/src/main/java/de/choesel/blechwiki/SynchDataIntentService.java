package de.choesel.blechwiki;

import android.app.IntentService;
import android.app.NotificationManager;
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


    public SynchDataIntentService() {
        super("SynchDataIntentService");
    }

    public static void startSynchonisation(Context context) {
        Intent intent = new Intent(context, SynchDataIntentService.class);
        context.startService(intent);
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

            mBuilder.setProgress(15000,0,false);
            mNotifyManager.notify(2709,mBuilder.build());

            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            BlechWikiRepository blechWikiRepository = new BlechWikiRepository(databaseHelper);

            int i = 0;
            for (Buch b : BlaeserWikiFactory.getBuecher()) {
                blechWikiRepository.saveOrUpdateBuch(b);
                mBuilder.setProgress(15000,i++,false);
                mNotifyManager.notify(2709,mBuilder.build());
            }


            for (Komponist k : BlaeserWikiFactory.getKomponisten()) {
                blechWikiRepository.saveOrUpdateKomponist(k);
                mBuilder.setProgress(15000,i++,false);
                mNotifyManager.notify(2709,mBuilder.build());
            }

            for (String s : BlaeserWikiFactory.getTitelNamen("")) {
                Log.d("Titel", s);
                for (Titel t : BlaeserWikiFactory.getFundStellen(s, databaseHelper)) {
                    blechWikiRepository.saveOrUpdateTitel(t);
                    mBuilder.setProgress(15000,i++,false);
                    mNotifyManager.notify(2709,mBuilder.build());
                }
            }


            databaseHelper.close();

            mNotifyManager.cancel(2709);
        }
    }


}
