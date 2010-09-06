package de.cismet.cids.custom.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author stefan
 */
public class TimestampConverter extends Converter<Timestamp, String> {

    public static final TimestampConverter INSTANCE = new TimestampConverter();

    public static TimestampConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public String convertForward(Timestamp value) {
        if (value == null) {
            return "";
        } else {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(value);
        }
    }

    @Override
    public Timestamp convertReverse(String value) {
        //not necessary. maybe it doesn't work that way because of formatting
//            return Timestamp.valueOf(value);
        return null;
    }
}
