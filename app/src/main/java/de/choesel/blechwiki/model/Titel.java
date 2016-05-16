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

import de.choesel.blechwiki.orm.URLType;

/**
 * Created by christian on 05.05.16.
 */
@DatabaseTable(tableName = "titel")
public class Titel{

    @DatabaseField(generatedId = true)
    private UUID id;

    @DatabaseField(persisterClass = URLType.class)
    private URL imgURL;

    @DatabaseField(canBeNull = true)
    private String vorzeichen;

    @DatabaseField(canBeNull = true)
    private String besetzung;

    @DatabaseField(canBeNull = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private String nummer;

    @DatabaseField(canBeNull = true)
    private String zusatz;

    @DatabaseField(canBeNull = true)
    private String komponist;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Buch buch;


    public Titel() {
        //Default Konstruktor
    }

    public Titel(final SoapObject soapObject) {

        if (soapObject.hasProperty("BuchId")) {
            Object obj = soapObject.getProperty("BuchId");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("BuchId");
                //TODO: Referenz zum Buch
//                name = j0.toString();
            }
        }

        if (soapObject.hasProperty("TITEL")) {
            Object obj = soapObject.getProperty("TITEL");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("TITEL");
                name = j0.toString();
            }
        }
        if (soapObject.hasProperty("Nr")) {
            Object obj = soapObject.getProperty("Nr");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Nr");
                nummer = j0.toString();
            }
        }

        if (soapObject.hasProperty("Zus")) {
            Object obj = soapObject.getProperty("Zus");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Zus");
                zusatz = j0.toString();
            }
        }

        if (soapObject.hasProperty("Komponist")) {
            Object obj = soapObject.getProperty("Komponist");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Komponist");
//                TODO: Refernenz zum Komponisten
//                    komponist = j0.toString();
            }
        }

        if (soapObject.hasProperty("Besetzung")) {
            Object obj = soapObject.getProperty("Besetzung");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Besetzung");
                besetzung = j0.toString();
            }
        }

        if (soapObject.hasProperty("Vorzeich")) {
            Object obj = soapObject.getProperty("Vorzeich");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Vorzeich");
                vorzeichen = j0.toString();
            }
        }

        if (soapObject.hasProperty("ImgURL")) {
            Object obj = soapObject.getProperty("ImgURL");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("ImgURL");
                try {
                    imgURL = new URL(j0.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public URL getImgURL() {
        return imgURL;
    }

    public void setImgURL(URL imgURL) {
        this.imgURL = imgURL;
    }

    public String getVorzeichen() {
        return vorzeichen;
    }

    public void setVorzeichen(String vorzeichen) {
        this.vorzeichen = vorzeichen;
    }

    public String getBesetzung() {
        return besetzung;
    }

    public void setBesetzung(String besetzung) {
        this.besetzung = besetzung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getZusatz() {
        return zusatz;
    }

    public void setZusatz(String zusatz) {
        this.zusatz = zusatz;
    }

    public String getKomponist() {
        return komponist;
    }

    public void setKomponist(String komponist) {
        this.komponist = komponist;
    }

    public Buch getBuch() {
        return buch;
    }

    public void setBuch(Buch buch) {
        this.buch = buch;
    }
}
