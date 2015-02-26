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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.cismet.cids.custom.objecteditors.wrrl_db_mv.SimSimulationsabschnittEditor;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.CidsBeanCollectionStore;

import de.cismet.tools.gui.jbands.DefaultBand;
import de.cismet.tools.gui.jbands.interfaces.BandMember;

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

    private Collection<CidsBean> beans = new ArrayList<CidsBean>();
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;

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

    /**
     * An invacation of this method is the same as an invocation of the method <code>setCidsBeans(beans, new
     * HashMap());</code>
     *
     * @param  beans  DOCUMENT ME!
     */
    @Override
    public void setCidsBeans(final Collection<CidsBean> beans) {
        setCidsBeans(beans, new HashMap<CidsBean, List<CidsBean>>(), new HashMap<CidsBean, Boolean>());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  beans                  DOCUMENT ME!
     * @param  massnahmenMap          DOCUMENT ME!
     * @param  massnahmenCompleteMap  DOCUMENT ME!
     */
    public void setCidsBeans(final Collection<CidsBean> beans,
            final Map<CidsBean, List<CidsBean>> massnahmenMap,
            final Map<CidsBean, Boolean> massnahmenCompleteMap) {
        this.beans = beans;
        if (beans != null) {
            for (final CidsBean b : beans) {
                final ReadOnlyFgskBandMember unrm = new ReadOnlyFgskBandMember(massnahmenMap.get(b),
                        massnahmenCompleteMap.get(b));
                unrm.setCidsBean(b);
                addMember(unrm);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  bean        DOCUMENT ME!
     * @param  massnahmen  DOCUMENT ME!
     */
    public void refreshMembers(final CidsBean bean, final List<CidsBean> massnahmen) {
        for (final BandMember m : members) {
            if (m instanceof ReadOnlyFgskBandMember) {
                final ReadOnlyFgskBandMember fgskMember = (ReadOnlyFgskBandMember)m;

                if (fgskMember.getCidsBean().equals(bean)) {
                    ((ReadOnlyFgskBandMember)m).refresh(massnahmen);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  min  DOCUMENT ME!
     */
    public void setMin(final double min) {
        this.min = min;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  max  DOCUMENT ME!
     */
    public void setMax(final double max) {
        this.max = max;
    }

    @Override
    public double getMin() {
        if (this.min == Double.MAX_VALUE) {
            return super.getMin();
        } else {
            return this.min;
        }
    }

    @Override
    public double getMax() {
        if (this.max == Double.MIN_VALUE) {
            return super.getMax();
        } else {
            return this.max;
        }
    }
}
