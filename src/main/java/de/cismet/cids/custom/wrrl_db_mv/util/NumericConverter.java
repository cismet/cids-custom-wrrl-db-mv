/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.jdesktop.beansbinding.Converter;

import java.math.BigDecimal;

import java.text.DecimalFormat;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class NumericConverter extends Converter<BigDecimal, String> {

    //~ Static fields/initializers ---------------------------------------------

    private static final NumericConverter INSTANCE = new NumericConverter();

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static NumericConverter getInstance() {
        return INSTANCE;
    }
    @Override
    public String convertForward(final BigDecimal value) {
        // log.fatal("forward:"+value);
        if (value != null) {
            return value.toString();
        } else {
            return null;
        }
    }

    @Override
    public BigDecimal convertReverse(final String value) {
        if (value != null) {
            try {
                return new BigDecimal(value);
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
    }
}
