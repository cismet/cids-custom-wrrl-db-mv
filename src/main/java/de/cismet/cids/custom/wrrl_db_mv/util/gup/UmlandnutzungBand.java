/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.wrrl_db_mv.util.gup;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.tools.gui.jbands.MinimumHeightBand;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class UmlandnutzungBand extends MinimumHeightBand implements CidsBeanCollectionStore {

    //~ Instance fields --------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */

    Collection<CidsBean> beans = new ArrayList<CidsBean>();

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UmlandnutzungsBand object.
     *
     * @param  side  DOCUMENT ME!
     */
    public UmlandnutzungBand(final String side) {
        super("Umlandnutzung " + side);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public Collection<CidsBean> getCidsBeans() {
        return beans;
    }

    @Override
    public void setCidsBeans(final Collection<CidsBean> beans) {
        this.beans = beans;
        if (beans != null) {
            for (final CidsBean b : beans) {
                final UmlandnutzungBandMember unrm = new UmlandnutzungBandMember();
                try {
                    unrm.setCidsBean(b);
                    addMember(unrm);
                } catch (Exception e) {
                    // dann halt nicht
                }
            }
        }
    }
}
