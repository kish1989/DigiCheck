package com.example.kishan.digicheck3;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class InsuranceCertificate extends ActionBarActivity {
    TextView regno, policy, dor, eor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurancecertificate);
        Intent intent = getIntent();
        String intentregno = intent.getStringExtra("REGNO");

        StrictMode.ThreadPolicy tpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tpolicy);

        regno = (TextView)findViewById(R.id.ic_regno);
        policy = (TextView)findViewById(R.id.ic_policy);
        dor = (TextView)findViewById(R.id.ic_dor);
        eor = (TextView)findViewById(R.id.ic_eor);

        LocalDBHandler localDBHandler = new LocalDBHandler(getApplicationContext());
        ICInformation ic = localDBHandler.getIc(intentregno);
        regno.setText(ic.getRegno());
        policy.setText(ic.getPolicyno());
        dor.setText(ic.getDateofissue());
        eor.setText(ic.getDateofexpiry());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insurancecertificate, menu);
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
