package de.choesel.blechwiki.orm;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import de.choesel.blechwiki.model.Buch;
import de.choesel.blechwiki.model.Komponist;
import de.choesel.blechwiki.model.Titel;

/**
 * Created by Christian on 14.05.2016.
 */
public class BlechWikiRepository {
    private final static String LOG_TAG = "BlechWikiRepository";

    private Dao<Buch, Integer> buchDao;
    private Dao<Komponist, Integer> komponistDao;
    private Dao<Titel, Integer> titelDao;

    public BlechWikiRepository(final DatabaseHelper databaseHelper) {
        komponistDao = getKomponistDao(databaseHelper);
        buchDao = getBuchDao(databaseHelper);
        titelDao = getTitelDao(databaseHelper);

    }

    public void clearData() {
        final List<Komponist> komponisten = getKomponisten();
        for (final Komponist komponist : komponisten) {
            deleteKomponist(komponist);
        }
    }

    public List<Komponist> getKomponisten() {
        try {
            return komponistDao.queryForAll();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Komponist>();
    }

    public List<Buch> getBuecher() {
        try {
            return buchDao.queryForAll();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Buch>();
    }

    public void saveOrUpdateKomponist(final Komponist komponist) {
        try {
            this.komponistDao.createOrUpdate(komponist);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateTitel(final Titel titel) {
        try {
            titelDao.createOrUpdate(titel);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateBuch(final Buch... buch) {
        try {
            buchDao.callBatchTasks(
                    new Callable<Void>() {
                        public Void call() throws SQLException {
                            for (Buch b : buch) {
                                buchDao.createOrUpdate(b);
                            }
                            return null;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteKomponist(final Komponist komponist) {
        try {
            //    final ForeignCollection<App> apps = komponist.getApps();
            //  for (final App app : apps) {
            //     this.buchDao.delete(app);
            // }
            this.komponistDao.delete(komponist);
        } catch (final SQLException e) {
            e.printStackTrace();
        }

    }

    private Dao<Komponist, Integer> getKomponistDao(final DatabaseHelper databaseHelper) {
        if (null == komponistDao) {
            try {
                komponistDao = databaseHelper.getKomponistDao();
            } catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return komponistDao;
    }

    private Dao<Buch, Integer> getBuchDao(final DatabaseHelper databaseHelper) {
        if (null == buchDao) {
            try {
                buchDao = databaseHelper.getBuchDao();
            } catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return buchDao;
    }

    private Dao<Titel, Integer> getTitelDao(final DatabaseHelper databaseHelper) {
        if (null == titelDao) {
            try {
                titelDao = databaseHelper.getTitelDao();
            } catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return titelDao;
    }
}