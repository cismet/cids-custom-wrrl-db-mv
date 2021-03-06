/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.jdesktop.beansbinding.Converter;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class RoundedNumberConverter extends Converter<Double, String> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RoundedNumberConverter.class);
    private static final RoundedNumberConverter INSTANCE = new RoundedNumberConverter();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static RoundedNumberConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public String convertForward(final Double value) {
        if (value == null) {
            return "";
        }
        double val = value.doubleValue();
        val = Math.round(val * 100.0) / 100.0;
        return String.valueOf(val);
    }

    @Override
    public Double convertReverse(final String value) {
        try {
            if (value == null) {
                return null;
            }

            return new Double(value.replace(',', '.'));
        } catch (final NumberFormatException e) {
            LOG.warn("No valid number: " + value, e);

            return null;
        }
    }
}
