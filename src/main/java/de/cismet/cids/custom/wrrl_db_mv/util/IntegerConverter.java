/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.Converter;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class IntegerConverter extends Converter<Integer, String> {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(IntegerConverter.class);
    private static final IntegerConverter INSTANCE = new IntegerConverter();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static IntegerConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public String convertForward(final Integer value) {
        return ((value == null) ? "" : value.toString());
    }

    @Override
    public Integer convertReverse(final String value) {
        try {
            if (value == null) {
                return null;
            }

            return new Integer(value);
        } catch (final NumberFormatException e) {
            LOG.warn("No valid number: " + value, e); // NOI18N

            return null;
        }
    }
}
