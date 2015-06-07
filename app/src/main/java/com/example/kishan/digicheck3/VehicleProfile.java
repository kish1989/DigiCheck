package com.example.kishan.digicheck3;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class VehicleProfile extends ActionBarActivity {
    TextView tv_uid;
    ListView lv_vlist;
    LocalDBHandler localDBHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicleprofile);
        Intent intent = getIntent();
        String uid = intent.getStringExtra("UID");

        StrictMode.ThreadPolicy tpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tpolicy);

        tv_uid = (TextView)findViewById(R.id.vp_aadharno);
        lv_vlist = (ListView)findViewById(R.id.vp_vlist);
        tv_uid.setText("Aadhaar number: " + uid);
        localDBHandler = new LocalDBHandler(getApplicationContext());

        //getVehicles(tv_uid.getText().toString());
        //getPCs(tv_uid.getText().toString());
        //getIcs(tv_uid.getText().toString());
        ArrayList<RCInformation> rcs = new ArrayList<RCInformation>();
        rcs = localDBHandler.getRcs(Long.valueOf(tv_uid.getText().toString().substring(16)));

        VehicleProfileAdapter vpa = new VehicleProfileAdapter(getApplicationContext(),R.layout.vehicleprofile_adapter,rcs);
        lv_vlist.setAdapter(vpa);

        lv_vlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView regno = (TextView)view.findViewById(R.id.vpa_regno);
                Intent intent = new Intent(getApplication(),VehicleSummary.class);
                intent.putExtra("REGNO",regno.getText().toString());
                startActivity(intent);
            }
        });
    }

   /* public void getVehicles(String aadhar)
    {
        RCInformation rcInformation;
        String URL = "https://hack-digicheck.herokuapp.com/getrcbyuid.php?uid="+aadhar.substring(16);
        HTTPGetPost httpGetPost = new HTTPGetPost();
        try {
            JSONArray jsonArray = httpGetPost.getJSONArray(URL);
            ParseJSONArray parseJSONArray = new ParseJSONArray();
            ArrayList<RCInformation> rcs = new ArrayList<RCInformation>();
            rcs = parseJSONArray.getRcs(jsonArray);
            long addtest = 0;
            localDBHandler.clearAllTables();
            for(RCInformation rc : rcs)
            {
                long added = localDBHandler.addRC(rc);
                if(added > 0)
                {
                    addtest++;
                }
            }
            if(rcs.size()>0)
            {
                VehicleProfileAdapter vehicleProfileAdapter = new VehicleProfileAdapter(getApplicationContext(),R.layout.vehicleprofile_adapter,rcs);
                lv_vlist.setAdapter(vehicleProfileAdapter);
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void getPCs(String aadhar)
    {
        PCInformation pcInformation;
        String URL = "https://hack-digicheck.herokuapp.com/getpcbyuid.php?uid="+aadhar.substring(16);
        HTTPGetPost httpGetPost = new HTTPGetPost();
        try {
            JSONArray jsonArray = httpGetPost.getJSONArray(URL);
            ParseJSONArray parseJSONArray = new ParseJSONArray();
            ArrayList<PCInformation> pcs = new ArrayList<PCInformation>();
            pcs = parseJSONArray.getPcs(jsonArray);
            long addtest = 0;
            //localDBHandler.clearAllTables();
            for(PCInformation pc : pcs)
            {
                long added = localDBHandler.addPC(pc);
                if(added > 0)
                {
                    addtest++;
                }
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void getIcs(String aadhar)
    {
        ICInformation icInformation;
        String URL = "https://hack-digicheck.herokuapp.com/geticbyuid.php?uid="+aadhar.substring(16);
        HTTPGetPost httpGetPost = new HTTPGetPost();
        try {
            JSONArray jsonArray = httpGetPost.getJSONArray(URL);
            ParseJSONArray parseJSONArray = new ParseJSONArray();
            ArrayList<ICInformation> ics = new ArrayList<ICInformation>();
            ics = parseJSONArray.getIcs(jsonArray);
            long addtest = 0;
            //localDBHandler.clearAllTables();
            for(ICInformation ic : ics)
            {
                long added = localDBHandler.addIc(ic);
                if(added > 0)
                {
                    addtest++;
                }
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicleprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
