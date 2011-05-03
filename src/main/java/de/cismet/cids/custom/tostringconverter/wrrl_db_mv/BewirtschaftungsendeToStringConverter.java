/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.tostringconverter.wrrl_db_mv;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;

import de.cismet.cids.custom.util.BewirtschaftungsendeHelper;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class BewirtschaftungsendeToStringConverter extends CustomToStringConverter {

    //~ Instance fields --------------------------------------------------------

    private final BewirtschaftungsendeHelper helper = new BewirtschaftungsendeHelper();
    private String string;
    private CidsBean oldBean;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new BewirtschaftungsendeToStringConverter object.
     */
    public BewirtschaftungsendeToStringConverter() {
        helper.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(final PropertyChangeEvent evt) {
                    if ((evt.getSource() == helper)
                                && evt.getPropertyName().equals(BewirtschaftungsendeHelper.PROP_WK)) {
                        final CidsBean wkBean = (CidsBean)evt.getNewValue();
                        string = (wkBean != null) ? (String)wkBean.getProperty("wk_k") : "<nicht gefunden>";
                    }
                }
            });
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public String createString() {
        if (cidsBean != oldBean) {
            helper.setCidsBean(cidsBean);
            oldBean = cidsBean;
        }
        return "Bewirtschaftungsende von " + string;
    }
}
