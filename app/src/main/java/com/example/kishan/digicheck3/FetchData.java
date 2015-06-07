package com.example.kishan.digicheck3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kishan on 07-06-2015.
 */
public class FetchData extends AsyncTask<Void, Void, Void> {
    String aaduid;
    Context ctx;
    LocalDBHandler localDBHandler;
    ProgressDialog mDialog;
    public FetchData(String uid, Context context)
    {
        this.aaduid = uid;
        this.ctx = context;
        localDBHandler = new LocalDBHandler(ctx);
    }
    @Override
    protected void onPreExecute() {
        // dialog = new ProgressDialog(MainActivity.this);
        //set message of the dialog
        // dialog.setMessage("Loading...");
        //show dialog
        //dialog.show();
        //super.onPreExecute();
        mDialog = new ProgressDialog(ctx);
        mDialog.setCancelable(true);
        mDialog.setMessage("Please wait while fetching your vehicle details from the server");
        mDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        getVehicles(aaduid);
        getPCs(aaduid);
        getIcs(aaduid);
        return null;
    }

    protected void onPostExecute(Void result) {
        // do UI work here
        if ((mDialog != null) && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        //if (dialog != null && dialog.isShowing()) {
        //  dialog.dismiss();
        Intent intent = new Intent(ctx,VehicleProfile.class);

        intent.putExtra("UID", aaduid);
        ctx.startActivity(intent);
        // }
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
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_LONG).show();
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
            Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_LONG).show();
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
            Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(ctx,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
