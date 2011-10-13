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

import java.util.Comparator;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class QualityStatusCodeComparator implements Comparator<CidsBean> {

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new CustomElementComparator object.
     */
    public QualityStatusCodeComparator() {
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public int compare(final CidsBean o1, final CidsBean o2) {
        if ((o1 != null) && (o2 != null)) {
            final String strValue1 = (String)o1.getProperty("value");
            final String strValue2 = (String)o2.getProperty("value");

            if ((strValue1 != null) && (strValue2 != null)) {
                try {
                    final Integer value1 = Integer.parseInt(strValue1);
                    final Integer value2 = Integer.parseInt(strValue2);

                    return value1.intValue() - value2.intValue();
                } catch (NumberFormatException e) {
                    // nothing to do, because not every 'value'-property contains a integer
                }

                return strValue1.compareTo(strValue2);
            } else {
                if ((strValue1 == null) && (strValue2 == null)) {
                    return 0;
                } else {
                    return ((strValue1 == null) ? -1 : 1);
                }
            }
        } else {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            } else {
                return ((o1 == null) ? -1 : 1);
            }
        }
    }
}
