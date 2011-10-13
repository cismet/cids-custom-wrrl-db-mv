/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2011 jruiz
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.wrrl_db_mv.util;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Collection;

import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.dynamics.DisposableCidsBeanStore;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class BewirtschaftungsendeHelper implements DisposableCidsBeanStore {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(
            BewirtschaftungsendeHelper.class);

    private static final MetaClass MC_WK_FG = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_fg");
    private static final MetaClass MC_WK_FG_TEILE = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            "wk_fg_teile");
    private static final MetaClass MC_WK_TEIL = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_teil");
    private static final MetaClass MC_STATION_LINIE = ClassCacheMultiple.getMetaClass(
            CidsBeanSupport.DOMAIN_NAME,
            "station_linie");
    private static final MetaClass MC_STATION = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "station");

    public static final String PROP_WK = "wk";

    //~ Instance fields --------------------------------------------------------

    private final Collection<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
    private CidsBean cidsBean;
    private CidsBean wkBean;
    private final Collection<CidsBean> wkFgBeans = new ArrayList<CidsBean>();
    private final PropertyChangeListener wertListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("wert")) {
                    updateWk();
                }
            }
        };

    private final PropertyChangeListener statListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                setCidsBean(cidsBean);
            }
        };

    //~ Methods ----------------------------------------------------------------

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  wkBean  DOCUMENT ME!
     */
    private void changeWkBean(final CidsBean wkBean) {
        final CidsBean oldWkBean = this.wkBean;
        this.wkBean = wkBean;
        fireWkChanged(oldWkBean, wkBean);
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        cleanListener();
        this.cidsBean = cidsBean;
        reinitWkFrontiers();
        initListener();
    }

    @Override
    public void dispose() {
        cleanListener();
    }

    /**
     * DOCUMENT ME!
     */
    private void initListener() {
        if (this.cidsBean != null) {
            this.cidsBean.addPropertyChangeListener(statListener);
            final CidsBean statBean = (CidsBean)cidsBean.getProperty("stat");
            if (statBean != null) {
                statBean.addPropertyChangeListener(wertListener);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void cleanListener() {
        if (this.cidsBean != null) {
            this.cidsBean.removePropertyChangeListener(statListener);
            final CidsBean statBean = (CidsBean)cidsBean.getProperty("stat");
            if (statBean != null) {
                statBean.removePropertyChangeListener(wertListener);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void reinitWkFrontiers() {
        wkFgBeans.clear();

        if ((cidsBean != null) && (cidsBean.getProperty("stat") != null)) {
            final int route_id = (Integer)cidsBean.getProperty("stat.route.id");

            final String queryWkFg = "SELECT "
                        + "   " + MC_WK_FG.getID() + ", "
                        + "   wk_fg." + MC_WK_FG.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + MC_WK_FG.getTableName() + " AS wk_fg, "
                        + "   " + MC_WK_FG_TEILE.getTableName() + " AS wk_fg_teile, "
                        + "   " + MC_WK_TEIL.getTableName() + " AS wk_teil, "
                        + "   " + MC_STATION_LINIE.getTableName() + " AS station_linie, "
                        + "   " + MC_STATION.getTableName() + " AS station "
                        + "WHERE "
                        + "   wk_fg_teile.wk_fg_reference = wk_fg.teile "
                        + "   AND wk_teil.id = wk_fg_teile.teil "
                        + "   AND station_linie.id = wk_teil.linie "
                        + "   AND station.id = station_linie.von "
                        + "   AND station.route = " + route_id + " "
                        + ";";
            try {
                final MetaObject[] mosWkFg;
                mosWkFg = SessionManager.getProxy().getMetaObjectByQuery(queryWkFg, 0);

                for (final MetaObject moWkFg : mosWkFg) {
                    wkFgBeans.add(moWkFg.getBean());
                }
            } catch (Exception ex) {
                LOG.error("error while loading wk_fgs", ex);
            }
        }
        updateWk();
    }

    /**
     * DOCUMENT ME!
     */
    private void updateWk() {
        try {
            for (final CidsBean wkFgBean : wkFgBeans) {
                for (final CidsBean teilBean : (Collection<CidsBean>)wkFgBean.getProperty("teile")) {
                    final double von = (Double)teilBean.getProperty("linie.von.wert");
                    final double bis = (Double)teilBean.getProperty("linie.bis.wert");
                    final double wert = (Double)cidsBean.getProperty("stat.wert");
                    if (((wert >= von) && (wert <= bis)) || ((wert >= bis) && (wert <= von))) {
                        changeWkBean(wkFgBean);
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            LOG.error("error while calculating WK", ex);
        }
        changeWkBean(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean addPropertyChangeListener(final PropertyChangeListener listener) {
        return listeners.add(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   listener  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public boolean removeListener(final PropertyChangeListener listener) {
        return listeners.remove(listener);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  oldWkBean  DOCUMENT ME!
     * @param  newWkBean  DOCUMENT ME!
     */
    private void fireWkChanged(final CidsBean oldWkBean, final CidsBean newWkBean) {
        for (final PropertyChangeListener listener : listeners) {
            listener.propertyChange(new PropertyChangeEvent(this, PROP_WK, oldWkBean, newWkBean));
        }
    }
}
