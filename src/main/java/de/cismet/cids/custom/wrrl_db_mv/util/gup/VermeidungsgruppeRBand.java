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
import java.util.List;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.tools.gui.jbands.MinimumHeightBand;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class VermeidungsgruppeRBand extends MinimumHeightBand {

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
     * @param  name  side DOCUMENT ME!
     */
    public VermeidungsgruppeRBand(final String name) {
        super(name);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  member  DOCUMENT ME!
     */
    public void addMember(final List<VermeidungsgruppeReadOnlyBandMember> member) {
        for (final VermeidungsgruppeReadOnlyBandMember tmp : member) {
            addMember(tmp);
        }
    }

//    @Override
//    public Collection<CidsBean> getCidsBeans() {
//        return beans;
//    }

//    @Override
//    public void setCidsBeans(final Collection<CidsBean> beans) {
//        this.beans = beans;
//        if (beans != null) {
//            for (final CidsBean b : beans) {
//                final Verme unrm = new ColoredReadOnlyBandMember();
//                try {
//                    unrm.setCidsBean(b, getTooltipProperty(), getColorProperty());
//                    addMember(unrm);
//                } catch (Exception e) {
//                    // dann halt nicht
//                }
//            }
//        }
//    }
}
