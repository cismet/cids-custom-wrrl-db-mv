/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.util.gup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.LinearReferencedLineEditor;

import de.cismet.cids.dynamics.CidsBean;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class UnterhaltungsabschnitteView implements ActionListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            UnterhaltungsabschnitteView.class);

    //~ Instance fields --------------------------------------------------------

    UnterhaltungsabschnitteModel model;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UnterhaltungsabschnitteView object.
     *
     * @param  model  DOCUMENT ME!
     */
    public UnterhaltungsabschnitteView(final UnterhaltungsabschnitteModel model) {
        this.model = model;
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        LOG.error("ActionCommand " + e.getActionCommand());
        if (model.isEmpty() && e.getActionCommand().equals("1, 0")) {
            final LinearReferencedLineEditor editor = model.getLineEditor();
            final CidsBean line = (CidsBean)editor.getCidsBean().getProperty("linie");
            if (line != null) {
                final CidsBean start = (CidsBean)line.getProperty("von");
                final CidsBean end = (CidsBean)line.getProperty("bis");
                final CidsBean route = (CidsBean)start.getProperty("route");
                final Long gwk = (Long)route.getProperty("gwk");
                final Integer startVal = (Integer)start.getProperty("value");
                final Integer endVal = (Integer)end.getProperty("value");

                LOG.error("gwk: " + gwk + " startVal: " + startVal + " endVal: " + endVal);
            }
        }
    }
}
