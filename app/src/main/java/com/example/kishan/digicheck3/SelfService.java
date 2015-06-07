package com.example.kishan.digicheck3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.aadhaarconnect.bridge.capture.model.auth.AuthCaptureRequest;
import com.aadhaarconnect.bridge.capture.model.auth.Demographics;
import com.aadhaarconnect.bridge.capture.model.common.Location;
import com.aadhaarconnect.bridge.capture.model.common.LocationType;
import com.aadhaarconnect.bridge.capture.model.common.request.CertificateType;
import com.aadhaarconnect.bridge.capture.model.common.request.Modality;
import com.aadhaarconnect.bridge.gateway.model.AuthResponse;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class SelfService extends ActionBarActivity {
    Button scanbtn, auth;
    EditText aaduid;
    EditText ssdob;
    UtilitiesClass utls;
    LocalDBHandler localDBHandler;
    ProgressDialog mDialog;
    private static final String BASE_URL="https://ac.khoslalabs.com/hackgate/hackathon";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfservice);

        scanbtn = (Button)findViewById(R.id.ss_scanBtn);
        auth = (Button)findViewById(R.id.ss_auth);
        aaduid = (EditText)findViewById(R.id.ss_aaduid);
        ssdob = (EditText)findViewById(R.id.ss_dob);
        utls = new UtilitiesClass();
        localDBHandler = new LocalDBHandler(getApplicationContext());
        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanBarcode(view);
            }
        });

        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authenticate(view);

                /*if(aaduid.getText().toString().trim().length() > 0) {
                    new getData().execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please scan QR code to get Aadhar no. or enter the number", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    private class getData extends AsyncTask<Void, Void, Void> {
        //ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // dialog = new ProgressDialog(MainActivity.this);
            //set message of the dialog
            // dialog.setMessage("Loading...");
            //show dialog
            //dialog.show();
            //super.onPreExecute();
            mDialog = new ProgressDialog(SelfService.this);
            mDialog.setCancelable(true);
            mDialog.setMessage("Please wait while fetching your details from the server");
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getVehicles(aaduid.getText().toString());
            getPCs(aaduid.getText().toString());
            getIcs(aaduid.getText().toString());
            return null;
        }

        protected void onPostExecute(Void result) {
            // do UI work here
            if ((mDialog != null) && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            //if (dialog != null && dialog.isShowing()) {
            //  dialog.dismiss();
            Intent intent = new Intent(getApplication(),VehicleProfile.class);

            intent.putExtra("UID", aaduid.getText().toString());
            startActivity(intent);
            // }
        }
    }

    public void authenticate(View v) {
        if (TextUtils.isEmpty(aaduid.getText())) {
            Toast.makeText(getApplicationContext(),"Scan QR or Enter an Aadhaar code",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(ssdob.getText()))
        {
            Toast.makeText(getApplicationContext(),"Enter a Date of birth before authenticating",Toast.LENGTH_LONG).show();
            return;
        }
        AuthCaptureRequest authCaptureRequest = new AuthCaptureRequest();
        authCaptureRequest.setAadhaar(aaduid.getText().toString());
        authCaptureRequest.setModality(Modality.demo);
        //authCaptureRequest.setModalityType(ModalityType.);
        //authCaptureRequest.setNumOffingersToCapture(2);
        authCaptureRequest.setDeviceId(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        authCaptureRequest.setCertificateType(CertificateType.preprod);

        Demographics demographics = new Demographics();
        Demographics.Dob dob = new Demographics.Dob();
        dob.setDobValue(ssdob.getText().toString());
        demographics.setDob(dob);
        authCaptureRequest.setDemographics(demographics);
        authCaptureRequest.setAutoSubmit(true);

        Location loc = new Location();
        loc.setType(LocationType.pincode);
        loc.setPincode("530002");
        authCaptureRequest.setLocation(loc);
        //aadhaarEditTextView.setText(String.valueOf(authCaptureRequest));
        //Toast.makeText(getApplicationContext(),new Gson().toJson(authCaptureRequest),Toast.LENGTH_LONG).show();

        Log.d("GSON.TOJSON",new Gson().toJson(authCaptureRequest));


        AadhaarAuthAsyncTask authAsyncTask = new AadhaarAuthAsyncTask(this,aaduid.getText().toString() ,authCaptureRequest);
        authAsyncTask.execute(BASE_URL + "/auth/raw");

    }


    public void scanBarcode(View view) {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                String xml = result.getContents();
                xml = utls.substringAfter(xml,">");

                //aaduid.setText(xml);
                ReadXML readXML = new ReadXML();
                try
                {
                    aaduid.setText(readXML.getAadharUID(xml.trim().toString()));
                    //Toast.makeText(getApplicationContext(),readXML.getAadharUID(result.getContents()),Toast.LENGTH_LONG).show();
                }
                catch(Exception e)
                {
                    //aaduid.setText(e.getMessage());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Log.d("MainActivity", "Weird");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getVehicles(String aadhar)
    {
        RCInformation rcInformation;
        String URL = "https://hack-digicheck.herokuapp.com/getrcbyuid.php?uid="+aadhar;
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
            /*if(rcs.size()>0)
            {
                VehicleProfileAdapter vehicleProfileAdapter = new VehicleProfileAdapter(getApplicationContext(),R.layout.vehicleprofile_adapter,rcs);
                lv_vlist.setAdapter(vehicleProfileAdapter);
            }*/
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void getPCs(String aadhar)
    {
        PCInformation pcInformation;
        String URL = "https://hack-digicheck.herokuapp.com/getpcbyuid.php?uid="+aadhar;
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
        String URL = "https://hack-digicheck.herokuapp.com/geticbyuid.php?uid="+aadhar;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selfservice, menu);
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
