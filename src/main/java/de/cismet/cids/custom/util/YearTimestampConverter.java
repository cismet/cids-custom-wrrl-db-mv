/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 therter
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.util;

import org.jdesktop.beansbinding.Converter;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Locale;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class YearTimestampConverter extends Converter<Timestamp, String> {

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
            return new SimpleDateFormat("yyyy", Locale.getDefault()).format(value);
        }
    }

    @Override
    public Timestamp convertReverse(final String value) {
        // not necessary. maybe it doesn't work that way because of formatting   //Now it is necessary
// return Timestamp.valueOf(value);
        final String formatString = "yyyy";
        final SimpleDateFormat sdf = new SimpleDateFormat(formatString, Locale.getDefault());
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
