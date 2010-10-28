package de.cismet.cids.custom.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author therter
 */
public class DateConverter extends Converter<Date, String> {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(YearTimestampConverter.class);//NOI18N
    private static final DateConverter INSTANCE = new DateConverter();

    public static DateConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public String convertForward(Date value) {
        if (value == null) {
            return "";
        } else {
            return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(value);
        }
    }

    @Override
    public Date convertReverse(String value) {
        //not necessary. maybe it doesn't work that way because of formatting
        return Date.valueOf(value);
    }
}
