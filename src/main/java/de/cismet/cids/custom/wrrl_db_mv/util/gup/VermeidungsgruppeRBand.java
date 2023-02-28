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

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class VermeidungsgruppeRBand extends CopyableBand {

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
        readOnly = true;
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
}
