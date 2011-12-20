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
public class CurrencyConverter extends Converter<BigDecimal, Number> {

    //~ Static fields/initializers ---------------------------------------------

    private static final CurrencyConverter INSTANCE = new CurrencyConverter();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static CurrencyConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public Number convertForward(final BigDecimal value) {
        return value;
    }

    @Override
    public BigDecimal convertReverse(final Number value) {
        try {
            if (value == null) {
                return null;
            }

            return BigDecimal.valueOf(value.doubleValue());
        } catch (final NumberFormatException e) {
            return null;
        }
    }
}
