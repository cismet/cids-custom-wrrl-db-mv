package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import de.cismet.cids.tools.CustomToStringConverter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author stefan
 */
public class ExDateToStringConverter extends CustomToStringConverter {

    @Override
    public String createString() {
        DateFormat sdf = SimpleDateFormat.getDateInstance();
        Object date = cidsBean.getProperty("date");
        if (date != null) {
            return String.valueOf(sdf.format(date));
        } else {
            return "";
        }
    }
}
