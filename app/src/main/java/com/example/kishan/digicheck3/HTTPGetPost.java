package com.example.kishan.digicheck3;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Kishan on 06-06-2015.
 */
public class HTTPGetPost {

    public JSONArray getJSONArray(String postUrl) throws IOException, JSONException
    {
        JSONArray jarray = null;

        InputStream is = null;
        String URL = postUrl;
        String result = "";
        HttpClient hclient = new DefaultHttpClient();
        HttpGet hget = new HttpGet(URL);
        HttpResponse response = null;
        response = hclient.execute(hget);
        HttpEntity hentity = response.getEntity();
        is = hentity.getContent();

        if(is != null)
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while(br.readLine()!=null)
            {
                sb.append(br.readLine());
            }
            br.close();
            result = sb.toString();

            jarray = new JSONArray(result);
        }

        return jarray;
    }
}
