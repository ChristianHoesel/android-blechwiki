package de.choesel.blechwiki.model;

import android.support.annotation.Nullable;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.choesel.blechwiki.orm.DatabaseHelper;

/**
 * Created by christian on 06.05.16.
 */
public final class BlaeserWikiFactory {


    private static final String SOAP_ACTION_GET_BUECHER = "http://pcportal.ddns.net/GetBücher";
    private static final String METHOD_NAME_GET_BUECHER = "GetBücher";

    private static final String SOAP_ACTION_GET_KOMPONISTEN = "http://pcportal.ddns.net/GetKomponisten";
    private static final String METHOD_NAME_GET_KOMPONISTEN = "GetKomponisten";

    private static final String SOAP_ACTION_GET_TITEL = "http://pcportal.ddns.net/GetTitel";
    private static final String METHOD_NAME_GET_TITEL = "GetTitel";

    private static final String SOAP_ACTION_GET_TITEL_FUNDSTELLE = "http://pcportal.ddns.net/GetTitelFundstellen";
    private static final String METHOD_NAME_GET_TITEL_FUNDSTELLE = "GetTitelFundstellen";

    private static final String NAMESPACE = "http://pcportal.ddns.net/";
    private static final String URL = "http://pcportal.ddns.net/LiteraService/service1.asmx?WSDL";

    private BlaeserWikiFactory() {
        //Konstruktor verstecken
    }


    public static List<Buch> getBuecher() {
        List<Buch> buchList = new ArrayList<>();

        SoapObject result = getExecuteSoapRequest(METHOD_NAME_GET_BUECHER, SOAP_ACTION_GET_BUECHER);
        if (result != null) {
            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) result.getProperty(i);
                if (property.hasProperty("NewDataSet")) {

                    SoapObject dataSet = (SoapObject) property.getPropertySafely("NewDataSet");
                    for (int j = 0; j < dataSet.getPropertyCount(); j++) {
                        SoapObject vBuecher = (SoapObject) dataSet.getProperty(j);
                        buchList.add(createBuch(vBuecher));
                    }
                }
            }
        }


        return buchList;
    }


    public static List<Komponist> getKomponisten() {
        List<Komponist> komponistenList = new ArrayList<>();

        SoapObject result = getExecuteSoapRequest(METHOD_NAME_GET_KOMPONISTEN, SOAP_ACTION_GET_KOMPONISTEN);
        if (result != null) {
            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) result.getProperty(i);
                Log.d("Komponisten", property.toString());
                if (property.hasProperty("NewDataSet")) {
                    Log.d("Komponisten", property.toString());
                    SoapObject dataSet = (SoapObject) property.getPropertySafely("NewDataSet");
                    for (int j = 0; j < dataSet.getPropertyCount(); j++) {
                        SoapObject vBuecher = (SoapObject) dataSet.getProperty(j);
                        komponistenList.add(createKomponist(vBuecher));
                    }
                }
            }
        }
        return komponistenList;
    }


    public static Set<String> getTitelNamen(final String suchstring) {
        Set<String> titelList = new HashSet<>();

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_TITEL);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.implicitTypes = true;
        envelope.dotNet = true;
        request.addProperty("Suchstring", suchstring);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION_GET_TITEL, envelope);
        } catch (XmlPullParserException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SoapObject result = null;
        try {
            result = (SoapObject) envelope.getResponse();
            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) result.getProperty(i);
                Log.d("Titel", property.toString());
                if (property.hasProperty("NewDataSet")) {
                    Log.d("Titel", property.toString());
                    SoapObject dataSet = (SoapObject) property.getPropertySafely("NewDataSet");
                    for (int j = 0; j < dataSet.getPropertyCount(); j++) {
                        SoapObject titel = (SoapObject) dataSet.getProperty(j);
                        if (titel.hasProperty("TITEL")) {
                            Object obj = titel.getProperty("TITEL");
                            if (obj.getClass().equals(SoapPrimitive.class)) {
                                SoapPrimitive j0 = (SoapPrimitive) titel.getProperty("TITEL");
                                titelList.add(j0.toString());
                            }
                        }

                    }
                }
            }
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return titelList;
    }

    public static List<Titel> getTitel(final String suchstring, final DatabaseHelper dbHelper) {
        List<Titel> titelList = new ArrayList<>();

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_TITEL);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.implicitTypes = true;
        envelope.dotNet = true;
        request.addProperty("Suchstring", suchstring);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION_GET_TITEL, envelope);
        } catch (XmlPullParserException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SoapObject result = null;
        try {
            result = (SoapObject) envelope.getResponse();
            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) result.getProperty(i);
                Log.d("Titel", property.toString());
                if (property.hasProperty("NewDataSet")) {
                    Log.d("Titel", property.toString());
                    SoapObject dataSet = (SoapObject) property.getPropertySafely("NewDataSet");
                    for (int j = 0; j < dataSet.getPropertyCount(); j++) {
                        SoapObject titel = (SoapObject) dataSet.getProperty(j);
                        if (titel.hasProperty("TITEL")) {
                            Object obj = titel.getProperty("TITEL");
                            if (obj.getClass().equals(SoapPrimitive.class)) {
                                SoapPrimitive j0 = (SoapPrimitive) titel.getProperty("TITEL");
                                titelList.addAll(getFundStellen(j0.toString(), dbHelper));
                            }
                        }

                    }
                }
            }
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return titelList;
    }


    public static List<Titel> getFundStellen(final String titelName, final DatabaseHelper dbHelper) {
        List<Titel> titelListe = new ArrayList<>();

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_TITEL_FUNDSTELLE);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.implicitTypes = true;
        envelope.dotNet = true;
        request.addProperty("vTitel", titelName);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION_GET_TITEL_FUNDSTELLE, envelope);
        } catch (XmlPullParserException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SoapObject result = null;
        try {
            result = (SoapObject) envelope.getResponse();

            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject property = (SoapObject) result.getProperty(i);
                Log.d("TitelFundstelle", property.toString());
                if (property.hasProperty("NewDataSet")) {
                    SoapObject dataSet = (SoapObject) property.getPropertySafely("NewDataSet");
                    for (int j = 0; j < dataSet.getPropertyCount(); j++) {
                        SoapObject titel = (SoapObject) dataSet.getProperty(j);

                        //titelListe.add(new Titel(titel));
                        titelListe.add(createTitel(titel, dbHelper));
                    }
                }
            }
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return titelListe;
    }


    @Nullable
    private static SoapObject getExecuteSoapRequest(String methodName, String soapAction) {
        SoapObject request = new SoapObject(NAMESPACE, methodName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.implicitTypes = true;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        httpTransport.debug = true;
        try {
            httpTransport.call(soapAction, envelope);
        } catch (XmlPullParserException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        SoapObject result = null;
        try {
            result = (SoapObject) envelope.getResponse();
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    private static Komponist createKomponist(final SoapObject soapObject) {
        Komponist result = new Komponist();
        if (soapObject.hasProperty("Komponist")) {
            Object obj = soapObject.getProperty("Komponist");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Komponist");
                result.setName(j0.toString());
            }
        }
        if (soapObject.hasProperty("kurz")) {
            Object obj = soapObject.getProperty("kurz");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("kurz");
                result.setKurzname(j0.toString());
            }
        }

        if (soapObject.hasProperty("Geboren")) {
            Object obj = soapObject.getProperty("Geboren");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Geboren");
                try {
                    result.setGeboren(Integer.valueOf(j0.toString()));
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
                    result.setGestorben(Integer.valueOf(j0.toString()));
                } catch (NumberFormatException e) {
                    //TODO: Fehlerbehandlung
                }
            }
        }

        return result;
    }

    private static Buch createBuch(final SoapObject soapObject) {
        Buch result = new Buch();
        if (soapObject.hasProperty("BuchId")) {
            Object obj = soapObject.getProperty("BuchId");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("BuchId");
                result.setBuchId(  j0.toString());
            }
        }
        if (soapObject.hasProperty("Buch")) {
            Object obj = soapObject.getProperty("Buch");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Buch");
                result.setTitel(j0.toString());
            }
        }

        if (soapObject.hasProperty("Untertitel")) {
            Object obj = soapObject.getProperty("Untertitel");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Untertitel");
                result.setUntertitel(j0.toString());
            }
        }

        if (soapObject.hasProperty("Erscheinjahr")) {
            Object obj = soapObject.getProperty("Erscheinjahr");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Erscheinjahr");
                result.setErscheinjahr(Integer.valueOf(j0.toString()));
            }
        }

        if (soapObject.hasProperty("Herausgeber")) {
            Object obj = soapObject.getProperty("Herausgeber");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Herausgeber");
                result.setHerausgeber(j0.toString());
            }
        }

        if (soapObject.hasProperty("Herausg_vorname")) {
            Object obj = soapObject.getProperty("Herausg_vorname");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Herausg_vorname");
                result.setHerausgeberVorname(j0.toString());
            }
        }

        if (soapObject.hasProperty("VERLAG")) {
            Object obj = soapObject.getProperty("VERLAG");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("VERLAG");
                result.setVerlag(j0.toString());
            }
        }

        if (soapObject.hasProperty("Verlagsnummer")) {
            Object obj = soapObject.getProperty("Verlagsnummer");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Verlagsnummer");
                result.setVerlagsnummer(j0.toString());
            }
        }

        if (soapObject.hasProperty("ImgUrl")) {
            Object obj = soapObject.getProperty("ImgUrl");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("ImgUrl");
                try {
                    result.setImgURL(new URL(j0.toString()));
                } catch (MalformedURLException e) {
                    //TODO Fehlerbehandlung
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private static Titel createTitel(final SoapObject soapObject, final DatabaseHelper dbHelper) {

        Titel result = new Titel();

        if (soapObject.hasProperty("BuchId")) {
            Object obj = soapObject.getProperty("BuchId");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("BuchId");
                //TODO: Referenz zum Buch
                String buchId = j0.toString();

                try {
                    Dao<Buch, Integer> buchDao = dbHelper.getBuchDao();
                    List<Buch> buches = buchDao.queryForEq("buchId", buchId);
                    for (Buch b : buches) {
                        result.setBuch(b);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }

        if (soapObject.hasProperty("TITEL")) {
            Object obj = soapObject.getProperty("TITEL");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("TITEL");
                result.setName(j0.toString());
            }
        }
        if (soapObject.hasProperty("Nr")) {
            Object obj = soapObject.getProperty("Nr");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Nr");
                result.setNummer(j0.toString());
            }
        }

        if (soapObject.hasProperty("Zus")) {
            Object obj = soapObject.getProperty("Zus");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Zus");
                result.setZusatz(j0.toString());
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
                result.setBesetzung(j0.toString());
            }
        }

        if (soapObject.hasProperty("Vorzeich")) {
            Object obj = soapObject.getProperty("Vorzeich");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("Vorzeich");
                result.setVorzeichen(j0.toString());
            }
        }

        if (soapObject.hasProperty("ImgURL")) {
            Object obj = soapObject.getProperty("ImgURL");
            if (obj.getClass().equals(SoapPrimitive.class)) {
                SoapPrimitive j0 = (SoapPrimitive) soapObject.getProperty("ImgURL");
                try {
                    result.setImgURL(new URL(j0.toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }


        return result;
    }


}
