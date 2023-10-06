package custom.lease.custom.view.beans.Utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class InvokeWebServices {
    public InvokeWebServices() {
        super();
    }
    
    /**
     * @param param
     * @param URLPort
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws JSONException
     */
    public static String invokeRestServicesNoParam(String URLPort) throws IOException, JSONException {
            String result=null;
            URL obj = new URL(URLPort);

            HttpURLConnection postConnection =
                (HttpURLConnection)obj.openConnection();
            postConnection.setRequestMethod("GET");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);
            int responseCode = postConnection.getResponseCode();
            StringBuffer response = new StringBuffer();
            String resMsg = null;

//            System.out.println("GET Response Code :  " + responseCode);
//            System.out.println("GET Response Message : " +postConnection.getResponseMessage());
            resMsg = postConnection.getResponseMessage();

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in =
                    new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
                StringBuilder out = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    out.append(inputLine);
                }
                in.close();
                // print result
               
//                JSONObject jsonObject = new JSONObject(response.toString());
//                 result = jsonObject.getString("status");
//                    JSFUtils.addFacesInformationMessage("Response : "+ response.toString());
//                System.out.println(response.toString());
//                System.out.println(result);
            } else {
//                System.out.println("GET NOT WORKED");
            }
            return response.toString();
        }
}
