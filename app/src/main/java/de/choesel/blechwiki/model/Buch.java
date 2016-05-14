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
@DatabaseTable(tableName = "buch")
public class Buch implements KvmSerializable {

    @DatabaseField(generatedId = true)
    private UUID id;

    @DatabaseField(canBeNull = true)
    private String buchId;

    @DatabaseField(canBeNull = true)
    private String titel;

    @DatabaseField(canBeNull = true)
    private String untertitel;

    @DatabaseField(canBeNull = true)
    private int erscheinjahr;

    @DatabaseField(canBeNull = true)
    private String herausgeber;

    @DatabaseField(canBeNull = true)
    private String herausgeberVorname;

    @DatabaseField(canBeNull = true)
    private String verlag;

    @DatabaseField(canBeNull = true)
    private String verlagsnummer;

    @DatabaseField(persisterClass = URLType.class)
    private URL imgURL;


    public Buch() {
        //Default Konstruktor
    }

    public Buch(final SoapObject soapObject) {


        if (soapObject.hasProperty("BuchId")) {
            Object obj = soapObject.getProperty("BuchId");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("BuchId");
                buchId = j0.toString();
            }
        }
        if (soapObject.hasProperty("Buch")) {
            Object obj = soapObject.getProperty("Buch");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Buch");
                titel = j0.toString();
            }
        }

        if (soapObject.hasProperty("Untertitel")) {
            Object obj = soapObject.getProperty("Untertitel");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Untertitel");
                untertitel = j0.toString();
            }
        }

        if (soapObject.hasProperty("Erscheinjahr")) {
            Object obj = soapObject.getProperty("Erscheinjahr");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Erscheinjahr");
                erscheinjahr = Integer.valueOf(j0.toString());
            }
        }

        if (soapObject.hasProperty("Herausgeber")) {
            Object obj = soapObject.getProperty("Herausgeber");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Herausgeber");
                herausgeber = j0.toString();
            }
        }

        if (soapObject.hasProperty("Herausg_vorname")) {
            Object obj = soapObject.getProperty("Herausg_vorname");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Herausg_vorname");
                herausgeberVorname = j0.toString();
            }
        }

        if (soapObject.hasProperty("VERLAG")) {
            Object obj = soapObject.getProperty("VERLAG");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("VERLAG");
                verlag = j0.toString();
            }
        }

        if (soapObject.hasProperty("Verlagsnummer")) {
            Object obj = soapObject.getProperty("Verlagsnummer");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Verlagsnummer");
                verlagsnummer = j0.toString();
            }
        }

        if (soapObject.hasProperty("ImgUrl")) {
            Object obj = soapObject.getProperty("ImgUrl");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("ImgUrl");
                try {
                    imgURL = new URL(j0.toString());
                } catch (MalformedURLException e) {
                    //TODO Fehlerbehandlung
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return buchId;
            case 1:
                return titel;
            case 2:
                return untertitel;
            case 3:
                return erscheinjahr;
            case 4:
                return herausgeber;
            case 5:
                return herausgeberVorname;
            case 6:
                return verlag;
            case 7:
                return verlagsnummer;
            case 8:
                return imgURL;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getPropertyCount() {
        return 9;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                buchId = value.toString();
                break;
            case 1:
                titel = value.toString();
                break;
            case 2:
                untertitel = value.toString();
                break;
            case 3:
                erscheinjahr = Integer.parseInt(value.toString());
                break;
            case 4:
                herausgeber = value.toString();
                break;
            case 5:
                herausgeberVorname = value.toString();
                break;
            case 6:
                verlag = value.toString();
                break;
            case 7:
                verlagsnummer = value.toString();
                break;
            case 8:
                try {
                    imgURL = new URL(value.toString());
                } catch (MalformedURLException e) {
                    //TODO fehlerbehandlung
                    e.printStackTrace();
                }
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
                info.name = "BuchId";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Buch";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Untertitel";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Erscheinjahr";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Herausgeber";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Herausg_vorname";
                break;
            case 6:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "VERLAG";
                break;
            case 7:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Verlagsnummer";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ImgUrl";
                break;
            default:
                throw new IllegalArgumentException();
        }
    }


    public String getBuchId() {
        return buchId;
    }

    public String getTitel() {
        return titel;
    }

    public String getUntertitel() {
        return untertitel;
    }

    public int getErscheinjahr() {
        return erscheinjahr;
    }

    public String getHerausgeber() {
        return herausgeber;
    }

    public String getHerausgeberVorname() {
        return herausgeberVorname;
    }

    public String getVerlag() {
        return verlag;
    }

    public String getVerlagsnummer() {
        return verlagsnummer;
    }

    public URL getImgURL() {
        return imgURL;
    }

    public void setBuchId(String buchId) {
        this.buchId = buchId;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setUntertitel(String untertitel) {
        this.untertitel = untertitel;
    }

    public void setErscheinjahr(int erscheinjahr) {
        this.erscheinjahr = erscheinjahr;
    }

    public void setHerausgeber(String herausgeber) {
        this.herausgeber = herausgeber;
    }

    public void setHerausgeberVorname(String herausgeberVorname) {
        this.herausgeberVorname = herausgeberVorname;
    }

    public void setVerlag(String verlag) {
        this.verlag = verlag;
    }

    public void setVerlagsnummer(String verlagsnummer) {
        this.verlagsnummer = verlagsnummer;
    }

    public void setImgURL(URL imgURL) {
        this.imgURL = imgURL;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
