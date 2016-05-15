package de.choesel.blechwiki.model;

import android.support.annotation.Nullable;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                        buchList.add(new Buch(vBuecher));
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
                        komponistenList.add(new Komponist(vBuecher));
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
        } catch (HttpResponseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //send request
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

    public static List<Titel> getTitel(final String suchstring) {
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
        } catch (HttpResponseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //send request
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
                                titelList.addAll(getFundStellen(j0.toString()));
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


    public static List<Titel> getFundStellen(final String titelName) {
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
        } catch (HttpResponseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //send request
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

                        titelListe.add(new Titel(titel));
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
        } catch (HttpResponseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //send request
        SoapObject result = null;
        try {
            result = (SoapObject) envelope.getResponse();
        } catch (SoapFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


}
