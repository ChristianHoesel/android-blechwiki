package de.choesel.blechwiki;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

import de.choesel.blechwiki.model.BlaeserWikiFactory;
import de.choesel.blechwiki.model.Buch;
import de.choesel.blechwiki.model.Komponist;
import de.choesel.blechwiki.model.Titel;
import de.choesel.blechwiki.orm.BlechWikiRepository;
import de.choesel.blechwiki.orm.DatabaseHelper;

/**
 * Created by Christian on 15.05.2016.
 */
public class SyncDataPreference extends DialogPreference {

    private ProgressBar mProgress;
    private TextView textBuecher;
    private TextView textKomponisten;
    private TextView textTitel;
    private myAsyncTask asyncTask;

    private class myAsyncTask extends AsyncTask<Void, Object, Void> {

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        private AtomicInteger anzahlBuecher = new AtomicInteger();
        private AtomicInteger anzahlKomponisten = new AtomicInteger();
        private AtomicInteger anzahlTitel = new AtomicInteger();

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            databaseHelper.close();
            mProgress.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            int progress = 0;

            BlechWikiRepository blechWikiRepository = new BlechWikiRepository(databaseHelper);
            for (Buch b : BlaeserWikiFactory.getBuecher()) {
                blechWikiRepository.saveOrUpdateBuch(b);
                publishProgress(b);
                if (isCancelled()) {
                    return null;
                }
            }


            for (Komponist k : BlaeserWikiFactory.getKomponisten()) {
                blechWikiRepository.saveOrUpdateKomponist(k);
                publishProgress(k);
                 if(isCancelled()){
                    return null;
                }
            }

            for (String s : BlaeserWikiFactory.getTitelNamen("")) {
                Log.d("Titel", s);
                for (Titel t : BlaeserWikiFactory.getFundStellen(s, databaseHelper)) {
                    blechWikiRepository.saveOrUpdateTitel(t);
                    publishProgress(t);
                    if (isCancelled()) {
                        return null;
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);

            for (Object o : values) {
                if (o instanceof Buch) {
                    textBuecher.setText(getContext().getText(R.string.buecher) + Integer.toString(anzahlBuecher.incrementAndGet()));
                } else if (o instanceof Komponist) {
                    textKomponisten.setText(getContext().getText(R.string.komponisten) + Integer.toString(anzahlKomponisten.incrementAndGet()));
                } else if (o instanceof Titel) {
                    textTitel.setText(getContext().getText(R.string.titel) + Integer.toString(anzahlTitel.incrementAndGet()));
                }
            }


        }
    }


    public SyncDataPreference(Context context,
                              AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.layout_sync_data_dialog);
    }

    @Override
    protected View onCreateDialogView() {
        View view = super.onCreateDialogView();

        mProgress = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgress.setMax(3);
        textBuecher = (TextView) view.findViewById(R.id.textBuecher);
        textKomponisten = (TextView) view.findViewById(R.id.textKomponisten);
        textTitel = (TextView) view.findViewById(R.id.textTitel);


        asyncTask = new myAsyncTask();
        asyncTask.execute();


        return view;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        asyncTask.cancel(true);
    }
}
