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
public class EntwicklungszielBand extends MinimumHeightBand implements CidsBeanCollectionStore {

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
     */
    public EntwicklungszielBand() {
        super("Entwicklungsziel");
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
                final EntwicklungszielBandMember unrm = new EntwicklungszielBandMember();
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
