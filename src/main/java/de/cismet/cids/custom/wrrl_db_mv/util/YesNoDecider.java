/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.server.middleware.types.MetaObject;

import java.util.ArrayList;
import java.util.List;

import de.cismet.cids.editors.FieldStateDecider;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class YesNoDecider implements FieldStateDecider {

    //~ Instance fields --------------------------------------------------------

    private List<MetaObject> positiveException = new ArrayList<MetaObject>();
    private List<MetaObject> negativeException = new ArrayList<MetaObject>();
    private boolean enable = true;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new YesNoDecider object.
     *
     * @param  enable  DOCUMENT ME!
     */
    public YesNoDecider(final boolean enable) {
        this.enable = enable;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public boolean isCheckboxForClassActive(final MetaObject mo) {
        final List<MetaObject> exception = (enable ? positiveException : negativeException);

        if (exception.contains(mo)) {
            return !enable;
        } else {
            return enable;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  the enable
     */
    public boolean isEnable() {
        return enable;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  enable  the enable to set
     */
    public void setEnable(final boolean enable) {
        this.enable = enable;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  mo  DOCUMENT ME!
     */
    public void addPositiveException(final MetaObject mo) {
        positiveException.add(mo);
    }
}
