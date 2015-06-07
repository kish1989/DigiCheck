package com.example.kishan.digicheck3;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kishan on 06-06-2015.
 */
public class UtilitiesClass {

    public String substringAfter(String fullString,String substr)
    {
        String PLBD;
        int indexofgtr = 0;
        char[] chars = fullString.toCharArray();
        for(int i = 0; i<chars.length; i++)
        {
            char letter = chars[i];
            if(Character.toString(letter).equals(substr))
            {
                indexofgtr = i;
                break;
            }
            else
            {
                continue;
            }
        }

        PLBD = fullString.substring(indexofgtr+1);
        return PLBD;
    }

    public Date getDateFormat(String dateval)
    {
        String[] dateparts = dateval.split("-");

        Calendar c1 = Calendar.getInstance();

        Date datevalue;
        datevalue = new Date();
        /*datevalue.setDate(Integer.parseInt(dateparts[0]));
        int year = Integer.parseInt(dateparts[2]);
        datevalue.setYear(year);*/
        c1.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateparts[0]));
        c1.set(Calendar.YEAR,Integer.parseInt(dateparts[2]));
        switch (dateparts[1].toUpperCase())
        {
            case "JAN": {
                //datevalue.setMonth(0);
                c1.set(Calendar.MONTH,0);
                break;
            }

            case "FEB": {
                //datevalue.setMonth(1);
                c1.set(Calendar.MONTH,1);
                break;
            }

            case "MAR": {
                //datevalue.setMonth(2);
                c1.set(Calendar.MONTH,2);
                break;
            }

            case "APR": {
                //datevalue.setMonth(3);
                c1.set(Calendar.MONTH,3);
                break;
            }

            case "MAY": {
                //datevalue.setMonth(4);
                c1.set(Calendar.MONTH,4);
                break;
            }

            case "JUN": {
                //datevalue.setMonth(5);
                c1.set(Calendar.MONTH,5);
                break;
            }

            case "JUL": {
                //datevalue.setMonth(6);
                c1.set(Calendar.MONTH,6);
                break;
            }

            case "AUG": {
                //datevalue.setMonth(7);
                c1.set(Calendar.MONTH,7);
                break;
            }

            case "SEP": {
                //datevalue.setMonth(8);
                c1.set(Calendar.MONTH,8);
                break;
            }

            case "OCT": {
                //datevalue.setMonth(9);
                c1.set(Calendar.MONTH,9);
                break;
            }

            case "NOV": {
                //datevalue.setMonth(10);
                c1.set(Calendar.MONTH,10);
                break;
            }

            case "DEC": {
                //datevalue.setMonth(11);
                c1.set(Calendar.MONTH,11);
                break;
            }
        }
        datevalue = c1.getTime();
        return datevalue;
    }
}
