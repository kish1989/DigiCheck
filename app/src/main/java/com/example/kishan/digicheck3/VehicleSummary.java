package com.example.kishan.digicheck3;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;


public class VehicleSummary extends ActionBarActivity {
    TextView ownername, regno,  rcdate,  pcdate,  icdate;
    Button rc, pc, ic, bd;
    LocalDBHandler localDBHandler;
    UtilitiesClass utls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclesummary);
        Intent intent = getIntent();
        final String intentregno = intent.getStringExtra("REGNO");

        StrictMode.ThreadPolicy tpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tpolicy);
        ownername = (TextView)findViewById(R.id.vs_ownername);
        regno = (TextView)findViewById(R.id.vs_regno);
        rc = (Button)findViewById(R.id.vs_rc);
        rcdate = (TextView)findViewById(R.id.vs_rceor);
        pc = (Button)findViewById(R.id.vs_pc);
        pcdate = (TextView)findViewById(R.id.vs_pcdoe);
        ic = (Button)findViewById(R.id.vs_ic);
        icdate = (TextView)findViewById(R.id.vs_icdoe);
        bd = (Button)findViewById(R.id.vs_bd);
        utls = new UtilitiesClass();
        localDBHandler = new LocalDBHandler(getApplicationContext());
        ownername.setText("Owner Name : " + localDBHandler.getRC(intentregno).getOwner());
        regno.setText("Registration Number : " + intentregno);
        rcdate.setText(localDBHandler.getRC(intentregno).getEor());
        pcdate.setText(localDBHandler.getPc(intentregno).getDoe());
        icdate.setText(localDBHandler.getIc(intentregno).getDateofexpiry());

        highlightdates();

        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BikeDetails.class);
                intent.putExtra("REGNO",intentregno);
                startActivity(intent);
            }
        });

        rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegistrationCertificate.class);
                intent.putExtra("REGNO",intentregno);
                startActivity(intent);
            }
        });

        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PollutionCertificate.class);
                intent.putExtra("REGNO",intentregno);
                startActivity(intent);
            }
        });

        ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),InsuranceCertificate.class);
                intent.putExtra("REGNO",intentregno);
                startActivity(intent);
            }
        });
    }

    public void highlightdates()
    {
        Date date = new Date();
        //Date date1 = utls.getDateFormat(rcdate.getText().toString());
        if(date.compareTo(utls.getDateFormat(rcdate.getText().toString())) > 0)
        {
            rcdate.setTextColor(getResources().getColor(R.color.certificate_expired));
        }
        else
        {
            rcdate.setTextColor(getResources().getColor(R.color.certificate_valid));
        }

        //Date date2 = utls.getDateFormat(pcdate.getText().toString());
        if(date.compareTo(utls.getDateFormat(pcdate.getText().toString())) > 0)
        {
            pcdate.setTextColor(getResources().getColor(R.color.certificate_expired));
        }
        else
        {
            pcdate.setTextColor(getResources().getColor(R.color.certificate_valid));
        }

        //Date date3 = utls.getDateFormat(icdate.getText().toString());
        if(date.compareTo(utls.getDateFormat(icdate.getText().toString())) > 0)
        {
            icdate.setTextColor(getResources().getColor(R.color.certificate_expired));
        }
        else
        {
            icdate.setTextColor(getResources().getColor(R.color.certificate_valid));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehiclesummary, menu);
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
