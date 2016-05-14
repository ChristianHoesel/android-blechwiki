package de.choesel.blechwiki.orm;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.choesel.blechwiki.model.Buch;
import de.choesel.blechwiki.model.Komponist;

/**
 * Created by Christian on 14.05.2016.
 */
public class BlechWikiRepository {
    private final static String LOG_TAG = "BlechWikiRepository";

    private Dao<Buch, Integer> buchDao;
    private Dao<Komponist, Integer> komponistDao;

    public BlechWikiRepository(final DatabaseHelper databaseHelper) {
        this.komponistDao = getKomponistDao(databaseHelper);
        this.buchDao = getBuchDao(databaseHelper);
    }

    public void clearData() {
        final List<Komponist> komponisten = getKomponisten();
        for (final Komponist komponist : komponisten) {
            deleteKomponist(komponist);
        }
    }

    public List<Komponist> getKomponisten() {
        try {
            return this.komponistDao.queryForAll();
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Komponist>();
    }

    public void saveOrUpdateKomponist(final Komponist komponist) {
        try {
            this.komponistDao.createOrUpdate(komponist);
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateBuch(final Buch buch) {
        try {
            this.buchDao.createOrUpdate(buch);
        }
        catch (final SQLException e) {
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
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }

    }

    private Dao<Komponist, Integer> getKomponistDao(final DatabaseHelper databaseHelper) {
        if (null == this.komponistDao) {
            try {
                this.komponistDao = databaseHelper.getKomponistDao();
            }
            catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return this.komponistDao;
    }

    private Dao<Buch, Integer> getBuchDao(final DatabaseHelper databaseHelper) {
        if (null == this.buchDao) {
            try {
                this.buchDao = databaseHelper.getBuchDao();
            }
            catch (final SQLException e) {
                Log.e(LOG_TAG, "Unable to load DAO: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return this.buchDao;
    }
}