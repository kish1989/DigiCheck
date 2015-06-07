package com.example.kishan.digicheck3;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class BikeDetails extends ActionBarActivity {
    TextView maker, type, chasis, engine, ftype, cc, wb, weight, color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikedetails);
        Intent intent = getIntent();
        String regno = intent.getStringExtra("REGNO");

        StrictMode.ThreadPolicy tpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tpolicy);

        maker = (TextView)findViewById(R.id.bd_maker);
        type = (TextView)findViewById(R.id.bd_type);
        chasis = (TextView)findViewById(R.id.bd_chasis);
        engine = (TextView)findViewById(R.id.bd_engine);
        ftype = (TextView)findViewById(R.id.bd_ftype);
        cc = (TextView)findViewById(R.id.bd_cc);
        wb = (TextView)findViewById(R.id.bd_wb);
        weight = (TextView)findViewById(R.id.bd_weight);
        color = (TextView)findViewById(R.id.bd_color);

        LocalDBHandler localDBHandler = new LocalDBHandler(getApplicationContext());
        RCInformation rc = localDBHandler.getRC(regno);
        maker.setText(rc.getMaker());
        type.setText(rc.getVehicletype());
        chasis.setText(rc.getChasisno());
        engine.setText(rc.getEngineno());
        ftype.setText(rc.getFueltype());
        cc.setText(String.valueOf(rc.getCc()));
        wb.setText(String.valueOf(rc.getWheelbase()));
        weight.setText(String.valueOf(rc.getWeight()));
        color.setText(rc.getColour());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bikedetails, menu);
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
