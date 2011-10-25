/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.jdesktop.beansbinding.Converter;

import java.math.BigDecimal;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class NumberConverter extends Converter<Double, String> {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(NumberConverter.class);
    private static final NumberConverter INSTANCE = new NumberConverter();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static NumberConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public String convertForward(final Double value) {
        return value.toString();
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
