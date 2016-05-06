package de.choesel.blechwiki.model;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 06.05.16.
 */
public final class BlaeserWikiFactory {


    private static final String SOAP_ACTION_GET_BUECHER = "http://pcportal.ddns.net/GetBücher";
    private static final String METHOD_NAME_GET_BUECHER = "GetBücher";
    private static final String NAMESPACE = "http://pcportal.ddns.net/";
    private static final String URL = "http://pcportal.ddns.net/LiteraService/service1.asmx?WSDL";

    private BlaeserWikiFactory(){
        //Konstruktor verstecken
    }


    public static List<Buch> getBuecher() {
        List<Buch> buchList = new ArrayList<>();

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_BUECHER);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.implicitTypes = true;
        envelope.dotNet = true;
//        envelope.addMapping(NAMESPACE, "vBücher", new Buch().getClass());
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(URL);

        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION_GET_BUECHER, envelope);
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


        return buchList;
    }


}
