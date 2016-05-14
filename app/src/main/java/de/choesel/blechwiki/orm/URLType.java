package de.choesel.blechwiki.orm;


import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.field.types.StringType;
import com.j256.ormlite.support.DatabaseResults;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by Christian on 14.05.2016.
 */
public class URLType extends BaseDataType {

    private static final URLType singleTon = new URLType();

    public static URLType getSingleton() {
        return singleTon;
    }

    private URLType() {
        super(SqlType.STRING, new Class<?>[] { URL.class });
    }


    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) throws SQLException {
        return defaultStr;
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        try {
            return new URL(results.getString(columnPos));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
