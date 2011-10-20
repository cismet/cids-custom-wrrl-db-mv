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

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.tools.gui.jbands.DefaultBand;
import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandPrefixProvider;
import de.cismet.tools.gui.jbands.interfaces.BandWeightProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class MassnahmenBand extends DefaultBand implements CidsBeanCollectionStore {

    //~ Instance fields --------------------------------------------------------

    Collection<CidsBean> massnahmen;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title  DOCUMENT ME!
     */
    public MassnahmenBand(final String title) {
        this(1f, title);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     */
    public MassnahmenBand(final float heightWeight) {
        super(heightWeight);
    }

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  heightWeight  DOCUMENT ME!
     * @param  title         DOCUMENT ME!
     */
    public MassnahmenBand(final float heightWeight, final String title) {
        super(heightWeight, title);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public CidsBean getRoute() {
        if ((massnahmen != null) && (massnahmen.size() > 0)) {
            for (final CidsBean cb : massnahmen) {
                return (CidsBean)cb.getProperty("linie.von.route");
            }
        }
        return null;
    }

    @Override
    public Collection<CidsBean> getCidsBeans() {
        return massnahmen;
    }

    @Override
    public void setCidsBeans(final Collection<CidsBean> beans) {
        massnahmen = beans;
        for (final CidsBean massnahme : massnahmen) {
            final MassnahmenBandMember m = new MassnahmenBandMember();
            m.setCidsBean(massnahme);
            addMember(m);
        }
    }
}
