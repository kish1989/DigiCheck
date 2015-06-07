package com.example.kishan.digicheck3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kishan on 06-06-2015.
 */
public class ParseJSONArray {
    public ArrayList<RCInformation> getRcs(JSONArray jsonArray) throws JSONException
    {
        RCInformation rcInformation;
        ArrayList<RCInformation> rcs = new ArrayList<RCInformation>();
        for(int i=0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            rcInformation = new RCInformation();
            rcInformation.setAadharno(Long.valueOf(jsonObject.getString("dc_rc_aadharno")));
            rcInformation.setRowid(Long.valueOf(jsonObject.getString("dc_rc_rowid")));
            rcInformation.setOwner(jsonObject.getString("dc_rc_owner"));
            rcInformation.setMaker(jsonObject.getString("dc_rc_maker"));
            rcInformation.setVehicletype(jsonObject.getString("dc_rc_vehicletype"));
            rcInformation.setChasisno(jsonObject.getString("dc_rc_chasisno"));
            rcInformation.setEngineno(jsonObject.getString("dc_rc_engineno"));
            rcInformation.setFueltype(jsonObject.getString("dc_rc_fueltype"));
            rcInformation.setCc(Float.valueOf(jsonObject.getString("dc_rc_cc")));
            rcInformation.setWheelbase(Float.valueOf(jsonObject.getString("dc_rc_wheelbase")));
            rcInformation.setWeight(Float.valueOf(jsonObject.getString("dc_rc_weight")));
            rcInformation.setColour(jsonObject.getString("dc_rc_colour"));
            rcInformation.setDor(jsonObject.getString("dc_rc_dor"));
            rcInformation.setEor(jsonObject.getString("dc_rc_eor"));
            rcInformation.setTaxpaid(Float.valueOf(jsonObject.getString("dc_rc_taxpaid")));
            rcInformation.setRegno(jsonObject.getString("dc_rc_regno"));
            rcs.add(rcInformation);
        }
        return rcs;
    }

    public ArrayList<PCInformation> getPcs(JSONArray jsonArray) throws JSONException
    {
        PCInformation pcinformation;
        ArrayList<PCInformation> pcs = new ArrayList<PCInformation>();
        for(int i=0; i<jsonArray.length(); i++)
        {
            pcinformation = new PCInformation();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            pcinformation.setRowid(Long.valueOf(jsonObject.getString("dc_pc_rowid")));
            pcinformation.setRegno(jsonObject.getString("dc_pc_regno"));
            pcinformation.setPtsno(jsonObject.getString("dc_pc_ptsno"));
            pcinformation.setDoe(jsonObject.getString("dc_pc_doe"));
            pcinformation.setTestactual(jsonObject.getString("dc_pc_testactual"));
            pcinformation.setTestregulation(jsonObject.getString("dc_pc_testreg"));
            pcinformation.setAadharno(Long.valueOf(jsonObject.getString("dc_pc_aadharno")));
            pcs.add(pcinformation);
        }
        return pcs;
    }

    public ArrayList<ICInformation> getIcs(JSONArray jsonArray) throws JSONException
    {
        ICInformation icInformation;
        ArrayList<ICInformation> ics = new ArrayList<ICInformation>();
        for(int i=0; i<jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            icInformation = new ICInformation();
            icInformation.setRowid(Long.valueOf(jsonObject.getString("dc_ic_rowid")));
            icInformation.setOwner(jsonObject.getString("dc_ic_owner"));
            icInformation.setPolicyno(jsonObject.getString("dc_ic_policyno"));
            icInformation.setDateofissue(jsonObject.getString("dc_ic_dateofissue"));
            icInformation.setDateofexpiry(jsonObject.getString("dc_ic_dateofexpiry"));
            icInformation.setAadharno(Long.valueOf(jsonObject.getString("dc_ic_aadhaarno")));
            icInformation.setRegno(jsonObject.getString("dc_ic_regno"));
            ics.add(icInformation);
        }
        return ics;
    }
}
