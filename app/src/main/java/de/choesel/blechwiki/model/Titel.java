package de.choesel.blechwiki.model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

/**
 * Created by christian on 05.05.16.
 */
public class Titel implements KvmSerializable {

    private URL imgURL;
    private String vorzeichen;
    private String besetzung;
    private String id;
    private String name;
    private String nummer;
    private String zusatz;
    private String komponist;


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
                id = j0.toString();
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

    @Override
    public Object getProperty(int index) {
        switch (index) {
            case 0:
                return name;
            case 1:
                return nummer;
            case 2:
                return zusatz;
            case 3:
                return komponist;
            case 4:
                return vorzeichen;
            case 5:
                return besetzung;
            case 6:
                return imgURL;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getPropertyCount() {
        return 7;
    }

    @Override
    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                name = value.toString();
                break;
            case 1:
                nummer = value.toString();
                break;
            case 2:
                zusatz = value.toString();
                break;
            case 3:
                komponist = value.toString();
                break;
            case 4:
                vorzeichen = value.toString();
                break;
            case 5:
                besetzung = value.toString();
                break;
            case 6:
                try {
                    imgURL = new URL(value.toString());
                } catch (MalformedURLException e) {
                    //TODO: Fehlerbehandlung
                    e.printStackTrace();
                }

            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public void getPropertyInfo(int index, Hashtable properties, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "TITEL";
                break;
            case 1:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Nr";
                break;
            case 2:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Zus";
                break;
            case 3:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Komponist";
                break;
            case 4:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Besetzung";
                break;
            case 5:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Vorzeich";
                break;
            case 6:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ImgURL";
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

}
