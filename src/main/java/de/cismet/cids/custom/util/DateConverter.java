/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util;

import org.jdesktop.beansbinding.Converter;

import java.sql.Date;

import java.text.SimpleDateFormat;

import java.util.Locale;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class DateConverter extends Converter<Date, String> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DateConverter.class); // NOI18N
    private static final DateConverter INSTANCE = new DateConverter();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static DateConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public String convertForward(final Date value) {
        if (value == null) {
            return "";
        } else {
            return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(value);
        }
    }

    @Override
    public Date convertReverse(final String value) {
        // not necessary. maybe it doesn't work that way because of formatting
        return Date.valueOf(value);
    }
}
