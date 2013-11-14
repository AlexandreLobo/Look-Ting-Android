package com.ool.lookting.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by arthurthompson on 9/27/13.
 */
public class LookTingUtil {

    public static String getFormattedDate(Calendar cal){
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy, h a");
        return sdf.format(cal.getTime());
    }
}
