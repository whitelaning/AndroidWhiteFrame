package com.whitelaning.whiteframe.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTool {

    public enum Type {
        YEAR, YEAR_MONTH, YEAR_MONTH_DAY, YEAR_MONTH_DAY_HOUR, YEAR_MONTH_DAY_HOUR_MINUTE, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND
    }

    public static String getFormatDate(Date date) {
        return getFormatDate(date, Type.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    public static String getFormatDate(Date date, Type type) {
        SimpleDateFormat df;
        switch (type) {
            case YEAR:
                df = new SimpleDateFormat("yyyy");
                break;
            case YEAR_MONTH:
                df = new SimpleDateFormat("yyyy-MM");
                break;
            case YEAR_MONTH_DAY:
                df = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case YEAR_MONTH_DAY_HOUR:
                df = new SimpleDateFormat("yyyy-MM-dd HH");
                break;
            case YEAR_MONTH_DAY_HOUR_MINUTE:
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                break;
            case YEAR_MONTH_DAY_HOUR_MINUTE_SECOND:
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            default:
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        return df.format(date);
    }
}
