/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.jdesktop.beansbinding.Converter;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Locale;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class TimestampConverter extends Converter<Timestamp, String> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(YearTimestampConverter.class); // NOI18N
    private static final TimestampConverter INSTANCE = new TimestampConverter();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static TimestampConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public String convertForward(final Timestamp value) {
        if (value == null) {
            return "";
        } else {
            return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(value);
        }
    }

    @Override
    public Timestamp convertReverse(final String value) {
        // not necessary. maybe it doesn't work that way because of formatting   //Now it is necessary
// return Timestamp.valueOf(value);
        final String formatString = "H:mm:ss dd.MM.yyyy";
        final SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        Timestamp timestamp = null;

        try {
            timestamp = new Timestamp(sdf.parse(value).getTime());
        } catch (ParseException e) {
            if (log.isDebugEnabled()) {
                log.debug("The string " + value + " cannot be converted to a timestamp. It should have the format "
                            + formatString);
            }
        }

        return timestamp;
    }
}
