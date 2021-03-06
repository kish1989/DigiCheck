package com.example.kishan.digicheck3;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class RegistrationCertificate extends ActionBarActivity {
    TextView regno, dor, eor, tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationcertificate);
        Intent intent = getIntent();
        String intentregno = intent.getStringExtra("REGNO");

        StrictMode.ThreadPolicy tpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tpolicy);

        regno = (TextView)findViewById(R.id.rc_regno);
        dor = (TextView)findViewById(R.id.rc_dor);
        eor = (TextView)findViewById(R.id.rc_eor);
        tax = (TextView)findViewById(R.id.rc_tax);

        LocalDBHandler localDBHandler = new LocalDBHandler(getApplicationContext());
        RCInformation rc = localDBHandler.getRC(intentregno);
        regno.setText(rc.getRegno());
        dor.setText(rc.getDor());
        eor.setText(rc.getEor());
        tax.setText(String.valueOf(rc.getTaxpaid()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrationcertificate, menu);
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
