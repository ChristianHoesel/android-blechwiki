package de.choesel.blechwiki.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import de.choesel.blechwiki.model.Buch;
import de.choesel.blechwiki.model.Komponist;
import de.choesel.blechwiki.model.Titel;

/**
 * Created by Christian on 14.05.2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "BlechWiki.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // the DAO object we use to access the SimpleData table
    private Dao<Buch, Integer> buchDao = null;
    private Dao<Komponist, Integer> komponistDao = null;
    private Dao<Titel, Integer> titelDao = null;

    @Override
    public void onCreate(final SQLiteDatabase db, final ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Buch.class);
            TableUtils.createTable(connectionSource, Komponist.class);
            TableUtils.createTable(connectionSource, Titel.class);
        }
        catch (final SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust the various data to
     * match the new version number.
     */
    @Override
    public void onUpgrade(final SQLiteDatabase db, final ConnectionSource connectionSource, final int oldVersion, final int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Buch.class, true);
            TableUtils.dropTable(connectionSource, Komponist.class, true);
            TableUtils.dropTable(connectionSource, Titel.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        }
        catch (final SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Buch, Integer> getBuchDao() throws SQLException {
        if (buchDao == null) {
            buchDao = getDao(Buch.class);
        }
        return buchDao;
    }

    public Dao<Komponist, Integer> getKomponistDao() throws SQLException {
        if (komponistDao == null) {
            komponistDao = getDao(Komponist.class);
        }
        return komponistDao;
    }

    public Dao<Titel, Integer> getTitelDao() throws SQLException{
        if(titelDao == null){
            titelDao = getDao(Titel.class);
        }
        return titelDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        komponistDao = null;
        buchDao = null;
        titelDao = null;
    }

}