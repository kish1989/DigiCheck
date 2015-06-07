package com.example.kishan.digicheck3;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class PollutionCertificate extends ActionBarActivity {
    TextView regno, ptc, eor, hcreg, hcact, coreg, coact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pollutioncertificate);
        Intent intent = getIntent();
        String intentregno = intent.getStringExtra("REGNO");

        StrictMode.ThreadPolicy tpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tpolicy);

        regno = (TextView)findViewById(R.id.pc_regno);
        ptc = (TextView)findViewById(R.id.pc_pts);
        eor = (TextView)findViewById(R.id.pc_eor);
        hcreg = (TextView)findViewById(R.id.pc_hcreg);
        hcact = (TextView)findViewById(R.id.pc_hcact);
        coreg = (TextView)findViewById(R.id.pc_coreg);
        coact = (TextView)findViewById(R.id.pc_coact);

        LocalDBHandler localDBHandler = new LocalDBHandler(getApplicationContext());
        PCInformation pc = localDBHandler.getPc(intentregno);
        regno.setText(pc.getRegno());
        ptc.setText(pc.getPtsno());
        eor.setText(pc.getDoe());
        hcreg.setText("HC: " + pc.getTestregulation().split(",")[1]);
        coreg.setText("CO: " + pc.getTestregulation().split(",")[0]);
        hcact.setText(pc.getTestactual().split(",")[1]);
        coact.setText(pc.getTestactual().split(",")[0]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pollutioncertificate, menu);
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
