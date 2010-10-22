package de.cismet.cids.custom.util;

import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author therter
 */
public class YearValidator extends Validator {
    @Override
    public Result validate(Object value) {
        String val = value.toString();

        try {
            if (val.length() == 4) {
                int intVal = Integer.parseInt(val);
                if (intVal > 1000 && intVal < 3000) {
                    return null;
                }
            }
        } catch (NumberFormatException e) {/*nothing to do*/}


        return new Result(1, "Der eingegebene Wert '" + val + "' ist kein gültiges Jahr");
    }
}
