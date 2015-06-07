package com.example.kishan.digicheck3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kishan on 06-06-2015.
 */
public class VehicleProfileAdapter extends ArrayAdapter<RCInformation> {
    public Context context;
    public int resourceInt;
    public ArrayList<RCInformation> rcs;
    public VehicleProfileAdapter(Context context, int resource, ArrayList<RCInformation> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceInt = resource;
        this.rcs = objects;
    }

    public View getView(int rowIndex, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if(null == row)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.vehicleprofile_adapter,null);
        }

        RCInformation rc = rcs.get(rowIndex);
        if(null != rc)
        {
            TextView regno = (TextView) row.findViewById(R.id.vpa_regno);
            TextView type = (TextView) row.findViewById(R.id.vpa_type);
            regno.setText(rc.getRegno());
            type.setText(rc.getVehicletype());
        }
        return row;
    }
}
