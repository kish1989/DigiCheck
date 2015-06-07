package com.example.kishan.digicheck3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kishan on 06-06-2015.
 */
public class LocalDBHandler extends SQLiteOpenHelper {
    private static final String dbname = "digicheck_localdb";
    private static final int dbversion = 1;
    private String[] tables = {"rc","pc","states","ic"};
    private String[] rcc = {"id","rowid","regno","owner","maker","vehicletype","chasisno","engineno","fueltype","cc","wheelbase","weight","colour","dor","eor","taxpaid","aadharno"};
    private String[] pcc = {"id","rowid","regno","ptsno","doe","testactual","testreg","aadharno"};
    private String[] state = {"id","name","code"};
    private String[] icc = {"id","rowid","owner","policyno","dateofissue","dateofexpiry","aadhaarno","regno"};

    public LocalDBHandler(Context context)
    {
        super(context, dbname, null, dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String[] createTables = new String[4];

        createTables[0] = "CREATE TABLE IF NOT EXISTS " + tables[0] + " ("+
                            rcc[0]+" INTEGER PRIMARY KEY AUTOINCREMENT";
        for(int i=1; i<rcc.length; i++)
        {
            createTables[0] = createTables[0] + ", " + rcc[i] + " TEXT";
        }
        createTables[0] = createTables[0] + ");";

        createTables[1] = "CREATE TABLE IF NOT EXISTS " + tables[1] + " ("+
                            pcc[0]+" INTEGER PRIMARY KEY AUTOINCREMENT";
        for(int j=1; j<pcc.length; j++)
        {
            createTables[1] = createTables[1] + ", " + pcc[j] + " TEXT";
        }
        createTables[1] = createTables[1] + ");";

        createTables[2] = "CREATE TABLE IF NOT EXISTS " + tables[2] + " ("+
                state[0]+" INTEGER PRIMARY KEY AUTOINCREMENT";
        for(int k=1; k<state.length; k++)
        {
            createTables[2] = createTables[2] + ", " + state[k] + " TEXT";
        }
        createTables[2] = createTables[2] + ");";

        createTables[3] = "CREATE TABLE IF NOT EXISTS " + tables[3] + " ("+
                icc[0]+" INTEGER PRIMARY KEY AUTOINCREMENT";
        for(int l=1; l<icc.length; l++)
        {
            createTables[3] = createTables[3] + ", " + icc[l] + " TEXT";
        }
        createTables[3] = createTables[3] + ");";

        for(int i = 0; i<=3; i++)
        {
            sqLiteDatabase.execSQL(createTables[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public void clearAllTables()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = 0;
        for(i=0; i<tables.length; i++)
        {
            sqLiteDatabase.delete(tables[i], null, null);
        }
    }

    public long addStateCodes(StateCode stateCode)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long added = 0;
        values.put(state[1],stateCode.getName());
        values.put(state[2],stateCode.getCode());

        added = sqLiteDatabase.insert(tables[2],null,values);
        return added;
    }

    public long addRC(RCInformation rcinfo)
    {
        long added = 0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(rcc[1],String.valueOf(rcinfo.getRowid()));
        values.put(rcc[2],String.valueOf(rcinfo.getRegno()));
        values.put(rcc[3],String.valueOf(rcinfo.getOwner()));
        values.put(rcc[4],String.valueOf(rcinfo.getMaker()));
        values.put(rcc[5],String.valueOf(rcinfo.getVehicletype()));
        values.put(rcc[6],String.valueOf(rcinfo.getChasisno()));
        values.put(rcc[7],String.valueOf(rcinfo.getEngineno()));
        values.put(rcc[8],String.valueOf(rcinfo.getFueltype()));
        values.put(rcc[9],String.valueOf(rcinfo.getCc()));
        values.put(rcc[10],String.valueOf(rcinfo.getWheelbase()));
        values.put(rcc[11],String.valueOf(rcinfo.getWeight()));
        values.put(rcc[12],String.valueOf(rcinfo.getColour()));
        values.put(rcc[13],String.valueOf(rcinfo.getDor()));
        values.put(rcc[14],String.valueOf(rcinfo.getEor()));
        values.put(rcc[15],String.valueOf(rcinfo.getTaxpaid()));
        values.put(rcc[16],String.valueOf(rcinfo.getAadharno()));

        added = sqLiteDatabase.insert(tables[0],null,values);
        return added;
    }

    public long addPC(PCInformation pcinfo)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long added = 0;
        ContentValues values = new ContentValues();
        values.put(pcc[1],String.valueOf(pcinfo.getRowid()));
        values.put(pcc[2],String.valueOf(pcinfo.getRegno()));
        values.put(pcc[3],String.valueOf(pcinfo.getPtsno()));
        values.put(pcc[4],String.valueOf(pcinfo.getDoe()));
        values.put(pcc[5],String.valueOf(pcinfo.getTestactual()));
        values.put(pcc[6],String.valueOf(pcinfo.getTestregulation()));
        values.put(pcc[7],String.valueOf(pcinfo.getAadharno()));

        added = sqLiteDatabase.insert(tables[1],null,values);
        return added;
    }

    public long addIc(ICInformation icinfo)
    {
        long added = 0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(icc[1],String.valueOf(icinfo.getRowid()));
        values.put(icc[2],String.valueOf(icinfo.getOwner()));
        values.put(icc[3],String.valueOf(icinfo.getPolicyno()));
        values.put(icc[4],String.valueOf(icinfo.getDateofissue()));
        values.put(icc[5],String.valueOf(icinfo.getDateofexpiry()));
        values.put(icc[6],String.valueOf(icinfo.getAadharno()));
        values.put(icc[7],String.valueOf(icinfo.getRegno()));
        added = sqLiteDatabase.insert(tables[3],null,values);
        return added;
    }

    public RCInformation getRC(String regno){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tables[0],rcc,rcc[2] + " = ?",new String[]{regno},null,null,null);
        RCInformation rc = new RCInformation();
        if(cursor.getCount() > 0)
        {
            //"rowid","regno","owner","maker","vehicletype","chasisno","engineno","fueltype","cc","wheelbase","weight","colour","dor","eor","taxpaid","aadharno"
            cursor.moveToFirst();
            rc.setRowid(Long.valueOf(cursor.getString(1)));
            rc.setRegno(cursor.getString(2));
            rc.setOwner(cursor.getString(3));
            rc.setMaker(cursor.getString(4));
            rc.setVehicletype(cursor.getString(5));
            rc.setChasisno(cursor.getString(6));
            rc.setEngineno(cursor.getString(7));
            rc.setFueltype(cursor.getString(8));
            rc.setCc(Float.valueOf(cursor.getString(9)));
            rc.setWheelbase(Float.valueOf(cursor.getString(10)));
            rc.setWeight(Float.valueOf(cursor.getString(11)));
            rc.setColour(cursor.getString(12));
            rc.setDor(cursor.getString(13));
            rc.setEor(cursor.getString(14));
            rc.setTaxpaid(Float.valueOf(cursor.getString(15)));
            rc.setAadharno(Long.valueOf(cursor.getString(16)));
        }

        return rc;
    }

    public ArrayList<RCInformation> getRcs(long UID)
    {
        ArrayList<RCInformation> rcs = new ArrayList<RCInformation>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tables[0],rcc,rcc[16] + " = ?",new String[]{String.valueOf(UID)},null,null,null);
        RCInformation rc;
        if(cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                rc = new RCInformation();
                rc.setRowid(Long.valueOf(cursor.getString(1)));
                rc.setRegno(cursor.getString(2));
                rc.setOwner(cursor.getString(3));
                rc.setMaker(cursor.getString(4));
                rc.setVehicletype(cursor.getString(5));
                rc.setChasisno(cursor.getString(6));
                rc.setEngineno(cursor.getString(7));
                rc.setFueltype(cursor.getString(8));
                rc.setCc(Float.valueOf(cursor.getString(9)));
                rc.setWheelbase(Float.valueOf(cursor.getString(10)));
                rc.setWeight(Float.valueOf(cursor.getString(11)));
                rc.setColour(cursor.getString(12));
                rc.setDor(cursor.getString(13));
                rc.setEor(cursor.getString(14));
                rc.setTaxpaid(Float.valueOf(cursor.getString(15)));
                rc.setAadharno(Long.valueOf(cursor.getString(16)));
                rcs.add(rc);
            }
        }
        return rcs;
    }

    public PCInformation getPc(String regno)
    {
        //"rowid","regno","ptsno","doe","testactual","testreg","aadharno"
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tables[1],pcc,pcc[2] + " = ?",new String[]{regno},null,null,null);
        PCInformation pc = new PCInformation();
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            pc.setRowid(Long.valueOf(cursor.getString(1)));
            pc.setRegno(cursor.getString(2));
            pc.setPtsno(cursor.getString(3));
            pc.setDoe(cursor.getString(4));
            pc.setTestactual(cursor.getString(5));
            pc.setTestregulation(cursor.getString(6));
            pc.setAadharno(Long.valueOf(cursor.getString(7)));
        }
        return pc;
    }

    public ICInformation getIc(String regno)
    {
        //"rowid","owner","policyno","dateofissue","dateofexpiry","aadhaarno","regno"
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tables[3],icc,icc[7] + " = ?",new String[]{regno},null,null,null);
        ICInformation ic = new ICInformation();

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            ic.setRowid(Long.valueOf(cursor.getString(1)));
            ic.setOwner(cursor.getString(2));
            ic.setPolicyno(cursor.getString(3));
            ic.setDateofissue(cursor.getString(4));
            ic.setDateofexpiry(cursor.getString(5));
            ic.setAadharno(Long.valueOf(cursor.getString(6)));
            ic.setRegno(cursor.getString(7));
        }
        return ic;
    }
}
