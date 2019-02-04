package com.abc.bank.abc.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

    private static DateUtility dateUtility = null;

    private DateUtility(){
    }

    public static DateUtility getInstance(){
        if(dateUtility == null){
            dateUtility = new DateUtility();
        }
        return dateUtility;
    }

    public boolean isSameWithCurrentDate(Date datePassed) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);

        Calendar lastTokenDate = Calendar.getInstance();
        lastTokenDate.setTime(datePassed);

        return currentDate.get(Calendar.DAY_OF_YEAR) == lastTokenDate.get(Calendar.DAY_OF_YEAR) &&
                currentDate.get(Calendar.YEAR) == lastTokenDate.get(Calendar.YEAR);
    }
}
