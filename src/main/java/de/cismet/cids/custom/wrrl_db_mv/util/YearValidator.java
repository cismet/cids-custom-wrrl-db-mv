/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import org.jdesktop.beansbinding.Validator;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class YearValidator extends Validator {

    //~ Methods ----------------------------------------------------------------

    @Override
    public Result validate(final Object value) {
        final String val = value.toString();

        try {
            if (val.length() == 4) {
                final int intVal = Integer.parseInt(val);
                if ((intVal > 1000) && (intVal < 3000)) {
                    return null;
                }
            }
        } catch (NumberFormatException e) { /*nothing to do*/
        }

        return new Result(1, "Der eingegebene Wert '" + val + "' ist kein gültiges Jahr");
    }
}
