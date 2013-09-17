/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.tools.gui.jbands.DefaultBand;
import de.cismet.tools.gui.jbands.MinimumHeightBand;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ReadOnlyFgskBand extends DefaultBand implements CidsBeanCollectionStore {

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
     * @param  name  the name of the band (The name will be shown on the left side)
     */
    public ReadOnlyFgskBand(final String name) {
        super(name);
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
                final ReadOnlyFgskBandMember unrm = new ReadOnlyFgskBandMember();
                unrm.setCidsBean(b);
                addMember(unrm);
            }
        }
    }
}
