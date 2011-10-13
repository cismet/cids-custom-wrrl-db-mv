/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 therter
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
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.jdesktop.beansbinding.Converter;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class YesNoConverter extends Converter<Boolean, String> {

    //~ Static fields/initializers ---------------------------------------------

    private static final YesNoConverter INSTANCE = new YesNoConverter();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static YesNoConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public String convertForward(final Boolean value) {
        if (value == null) {
            return CidsBeanSupport.FIELD_NOT_SET;
        } else {
            return (value.booleanValue() ? "ja" : "nein");
        }
    }

    @Override
    public Boolean convertReverse(final String value) {
        // not necessary.
        if (value == null) {
            return null;
        } else if (value.equalsIgnoreCase("ja") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")) {
            return true;
        } else {
            return false;
        }
    }
}
