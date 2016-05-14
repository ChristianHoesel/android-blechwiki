package de.choesel.blechwiki.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by christian on 05.05.16.
 */
@DatabaseTable(tableName = "komponist")
public class Komponist implements KvmSerializable {

    @DatabaseField(generatedId = true)
    private UUID id;

    @DatabaseField(canBeNull = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private String kurzname;

    @DatabaseField(canBeNull = true)
    private Integer geboren;

    @DatabaseField(canBeNull = true)
    private Integer gestorben;


    public Komponist() {
        //Default Konstruktor
    }

    public Komponist(final SoapObject soapObject) {


        if (soapObject.hasProperty("Komponist")) {
            Object obj = soapObject.getProperty("Komponist");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Komponist");
                name = j0.toString();
            }
        }
        if (soapObject.hasProperty("kurz")) {
            Object obj = soapObject.getProperty("kurz");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("kurz");
                kurzname = j0.toString();
            }
        }

        if (soapObject.hasProperty("Geboren")) {
            Object obj = soapObject.getProperty("Geboren");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Geboren");
                try {
                    geboren = Integer.valueOf(j0.toString());
                } catch (NumberFormatException e) {
                    //TODO: Fehlerbehandlung
                }
            }
        }

        if (soapObject.hasProperty("Gestorben")) {
            Object obj = soapObject.getProperty("Gestorben");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Gestorben");
                try {
                    gestorben = Integer.valueOf(j0.toString());
                } catch (NumberFormatException e) {
                    //TODO: Fehlerbehandlung
                }
            }
        }

    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return name;
            case 1:
                return kurzname;
            case 2:
                return geboren;
            case 3:
                return gestorben;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                name = value.toString();
                break;
            case 1:
                kurzname = value.toString();
                break;
            case 2:
                geboren = Integer.parseInt(value.toString());
                break;
            case 3:
                gestorben = Integer.parseInt(value.toString());
                break;
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Komponist";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "kurz";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Geboren";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Gestorben";
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKurzname() {
        return kurzname;
    }

    public Integer getGeboren() {
        return geboren;
    }

    public Integer getGestorben() {
        return gestorben;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKurzname(String kurzname) {
        this.kurzname = kurzname;
    }

    public void setGeboren(Integer geboren) {
        this.geboren = geboren;
    }

    public void setGestorben(Integer gestorben) {
        this.gestorben = gestorben;
    }
}
