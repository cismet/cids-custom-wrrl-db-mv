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

    Collection<CidsBean> beans = new ArrayList<CidsBean>();
    SimSimulationsabschnittEditor editor;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new UmlandnutzungsBand object.
     *
     * @param  name    the name of the band (The name will be shown on the left side)
     * @param  editor  DOCUMENT ME!
     */
    public ReadOnlyFgskBand(final String name, final SimSimulationsabschnittEditor editor) {
        super(name);
        this.editor = editor;
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
        setCidsBeans(beans, new HashMap<CidsBean, List<CidsBean>>());
    }

    /**
     * DOCUMENT ME!
     *
     * @param  beans          DOCUMENT ME!
     * @param  massnahmenMap  DOCUMENT ME!
     */
    public void setCidsBeans(final Collection<CidsBean> beans, final Map<CidsBean, List<CidsBean>> massnahmenMap) {
        this.beans = beans;
        if (beans != null) {
            for (final CidsBean b : beans) {
                final ReadOnlyFgskBandMember unrm = new ReadOnlyFgskBandMember(editor, massnahmenMap.get(b));
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
}
