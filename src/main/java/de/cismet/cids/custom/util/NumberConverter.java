/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util;

import org.jdesktop.beansbinding.Converter;

import java.math.BigDecimal;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class NumberConverter extends Converter<BigDecimal, String> {

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
    public String convertForward(final BigDecimal value) {
        return value.toString();
    }

    @Override
    public BigDecimal convertReverse(final String value) {
        try {
            if (value == null) {
                return null;
            }
            return new BigDecimal(value.replace(',', '.'));
        } catch (final NumberFormatException e) {
            LOG.warn("No valid number: " + value, e);
            return null;
        }
    }
}