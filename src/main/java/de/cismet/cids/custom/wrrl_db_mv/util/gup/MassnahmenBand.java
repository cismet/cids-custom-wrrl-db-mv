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

import de.cismet.tools.gui.jbands.JBand;
import de.cismet.tools.gui.jbands.SimpleBandModel;
import de.cismet.tools.gui.jbands.interfaces.Band;
import de.cismet.tools.gui.jbands.interfaces.BandMember;
import de.cismet.tools.gui.jbands.interfaces.BandPrefixProvider;
import de.cismet.tools.gui.jbands.interfaces.BandWeightProvider;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class MassnahmenBand implements Band, CidsBeanCollectionStore, BandPrefixProvider, BandWeightProvider {

    //~ Instance fields --------------------------------------------------------

    Collection<CidsBean> massnahmen;
    ArrayList<MassnahmenBandMember> mbm = new ArrayList<MassnahmenBandMember>();
    final JBand jbdTest = new JBand();
    final SimpleBandModel sbm = new SimpleBandModel();
    private JLabel label = new JLabel();
    private float bandWeight = 1;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new MassnahmenBand object.
     *
     * @param  title  DOCUMENT ME!
     */
    public MassnahmenBand(final String title) {
        label.setText(title);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
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
            mbm.add(m);
        }
    }

    @Override
    public double getMax() {
        double max = Double.MIN_VALUE;
        for (final MassnahmenBandMember m : mbm) {
            max = ((max < m.getMax()) ? m.getMax() : max);
        }
        return max;
    }

    @Override
    public BandMember getMember(final int i) {
        return mbm.get(i);
    }

    @Override
    public double getMin() {
        double min = Double.MAX_VALUE;
        for (final MassnahmenBandMember m : mbm) {
            min = ((min > m.getMin()) ? m.getMin() : min);
        }
        return min;
    }

    @Override
    public int getNumberOfMembers() {
        return (massnahmen != null) ? massnahmen.size() : 0;
    }

    @Override
    public JComponent getPrefixComponent() {
        return label;
    }

    @Override
    public float getBandWeight() {
        return bandWeight;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bandWeight  DOCUMENT ME!
     */
    public void setBandWeight(final float bandWeight) {
        this.bandWeight = bandWeight;
    }
}
