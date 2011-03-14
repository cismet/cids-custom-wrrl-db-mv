/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 jruiz
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
/*
 * QuerbauwerkeEditor.java
 *
 * Created on 26.10.2010, 13:09:43
 */
package de.cismet.cids.custom.objecteditors.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.DecimalFormat;

import de.cismet.cids.custom.util.CidsBeanSupport;
import de.cismet.cids.custom.util.LinearReferencingConstants;
import de.cismet.cids.custom.util.TabbedPaneUITransparent;
import de.cismet.cids.custom.util.WrrlEditorTester;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.EditorClosedEvent;
import de.cismet.cids.editors.EditorSaveListener;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.interaction.CismapBroker;

/**
 * DOCUMENT ME!
 *
 * @author   jruiz
 * @version  $Revision$, $Date$
 */
public class QuerbauwerkeEditor extends javax.swing.JPanel implements CidsBeanRenderer, EditorSaveListener {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(QuerbauwerkeEditor.class);

    //~ Instance fields --------------------------------------------------------

    private CidsBean cidsBean;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel panAllgemeines;
    private javax.swing.JPanel panBeschreibung;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanFive querbauwerkePanFive;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanFour querbauwerkePanFour;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanOne querbauwerkePanOne;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanSix querbauwerkePanSix;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanThree querbauwerkePanThree;
    private de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanTwo querbauwerkePanTwo;
    private javax.swing.JTabbedPane tpMain;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form QuerbauwerkeEditor.
     */
    public QuerbauwerkeEditor() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        initComponents();

        querbauwerkePanSix.getStat09Editor().addStationEditorListener(new StationEditorListener() {

                @Override
                public void stationCreated() {
                    try {
                        zoomToFeatures();
                        cidsBean.setProperty("stat09", querbauwerkePanSix.getStat09Editor().getCidsBean());
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("error while assigning stat09 cidsbean to cidsbean", ex);
                        }
                    }
                }
            });
        querbauwerkePanSix.getStat09BisEditor().addStationEditorListener(new StationEditorListener() {

                @Override
                public void stationCreated() {
                    try {
                        zoomToFeatures();
                        cidsBean.setProperty("stat09_bis", querbauwerkePanSix.getStat09BisEditor().getCidsBean());
                    } catch (Exception ex) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("error while assigning stat09_bis cidsbean to cidsbean", ex);
                        }
                    }
                }
            });

        tpMain.setUI(new TabbedPaneUITransparent());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    private void zoomToFeatures() {
        final MappingComponent mappingComponent = CismapBroker.getInstance().getMappingComponent();
        if (!mappingComponent.isFixedMapExtent()) {
            CismapBroker.getInstance()
                    .getMappingComponent()
                    .zoomToFeatureCollection(mappingComponent.isFixedMapScale());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        tpMain = new javax.swing.JTabbedPane();
        panBeschreibung = new javax.swing.JPanel();
        querbauwerkePanTwo = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanTwo();
        jPanel3 = new javax.swing.JPanel();
        querbauwerkePanThree = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanThree();
        querbauwerkePanFive = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanFive();
        querbauwerkePanSix = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanSix();
        panAllgemeines = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        querbauwerkePanOne = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanOne();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        querbauwerkePanFour = new de.cismet.cids.custom.objecteditors.wrrl_db_mv.QuerbauwerkePanFour();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        panBeschreibung.setOpaque(false);
        panBeschreibung.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panBeschreibung.add(querbauwerkePanTwo, gridBagConstraints);

        jPanel3.setOpaque(false);

        final javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panBeschreibung.add(jPanel3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 10);
        panBeschreibung.add(querbauwerkePanThree, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 0);
        panBeschreibung.add(querbauwerkePanFive, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panBeschreibung.add(querbauwerkePanSix, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.panBeschreibung.TabConstraints.tabTitle"),
            panBeschreibung); // NOI18N

        panAllgemeines.setOpaque(false);
        panAllgemeines.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);

        final javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                1025,
                Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                471,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        panAllgemeines.add(jPanel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        panAllgemeines.add(querbauwerkePanOne, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.panAllgemeines.TabConstraints.tabTitle"),
            panAllgemeines); // NOI18N

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel5.setOpaque(false);

        final javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                1025,
                Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                800,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jPanel5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel4.add(querbauwerkePanFour, gridBagConstraints);

        tpMain.addTab(org.openide.util.NbBundle.getMessage(
                QuerbauwerkeEditor.class,
                "QuerbauwerkeEditor.jPanel4.TabConstraints.tabTitle"),
            jPanel4); // NOI18N

        add(tpMain, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(final CidsBean cidsBean) {
        bindingGroup.unbind();
        this.cidsBean = cidsBean;
        if (cidsBean != null) {
            try {
                cidsBean.addPropertyChangeListener(new PropertyChangeListener() {

                        @Override
                        public void propertyChange(final PropertyChangeEvent pce) {
                            if (pce.getPropertyName().equals("bauwerk")) {
                                int bauwerk_value = 0;
                                try {
                                    bauwerk_value = Integer.parseInt(
                                            (String)((CidsBean)cidsBean.getProperty("bauwerk")).getProperty("value"));
                                } catch (Exception ex) {
                                }
                                querbauwerkePanFour.setWehrVisible(bauwerk_value == 1);
                                querbauwerkePanFour.setStarrVisible(bauwerk_value == 3);
                            }
                        }
                    });
                ((CidsBean)cidsBean.getProperty("stat09")).addPropertyChangeListener(new PropertyChangeListener() {

                        @Override
                        public void propertyChange(final PropertyChangeEvent pce) {
                            if (pce.getPropertyName().equals(LinearReferencingConstants.PROP_STATION_VALUE)) {
                                wertChanged();
                            }
                        }
                    });
            } catch (Exception ex) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("error while autosetting properties", ex);
                }
            }
            // aus Performancegründen nicht in wertChanged
            updateWaKoerper();

            querbauwerkePanOne.setCidsBean(cidsBean);
            querbauwerkePanTwo.setCidsBean(cidsBean);
            querbauwerkePanThree.setCidsBean(cidsBean);
            querbauwerkePanFour.setCidsBean(cidsBean);
            querbauwerkePanFive.setCidsBean(cidsBean);
            querbauwerkePanSix.setCidsBean(cidsBean);

            bindingGroup.bind();
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void wertChanged() {
        updateQbwId();
    }

    /**
     * DOCUMENT ME!
     */
    private void updateWaKoerper() {
        final MetaClass mcWkFg = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_fg");
        final MetaClass mcWkFgTeile = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_fg_teile");
        final MetaClass mcWkTeil = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_teil");
        final MetaClass mcLine = ClassCacheMultiple.getMetaClass(
                CidsBeanSupport.DOMAIN_NAME,
                LinearReferencingConstants.MC_STATIONLINE);
        final MetaClass mcStation = ClassCacheMultiple.getMetaClass(
                CidsBeanSupport.DOMAIN_NAME,
                LinearReferencingConstants.MC_STATION);
        final MetaClass mcRoute = ClassCacheMultiple.getMetaClass(
                CidsBeanSupport.DOMAIN_NAME,
                LinearReferencingConstants.MC_ROUTE);

        final MetaClass mcWkSg = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "wk_sg");
        final MetaClass mcQuerbauwerke = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "querbauwerke");
        final MetaClass mcGeom = ClassCacheMultiple.getMetaClass(CidsBeanSupport.DOMAIN_NAME, "geom");

        String queryWkFg = "";
        String queryWkSg = "";
        final int id = (Integer)cidsBean.getProperty("id");
        final CidsBean stat09 = (CidsBean)cidsBean.getProperty("stat09");
        if (stat09 != null) {
            final double wert = (Double)stat09.getProperty(LinearReferencingConstants.PROP_STATION_VALUE);
            final CidsBean route = (CidsBean)stat09.getProperty(LinearReferencingConstants.PROP_STATION_ROUTE);
            final long gwk = (Long)route.getProperty(LinearReferencingConstants.PROP_ROUTE_GWK);

            queryWkFg = "SELECT "
                        + "   " + mcWkFg.getID() + ", "
                        + "   wk_fg." + mcWkFg.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcWkFg.getTableName() + " AS wk_fg, "
                        + "   " + mcWkFgTeile.getTableName() + " AS wk_fg_teile, "
                        + "   " + mcWkTeil.getTableName() + " AS wk_teil, "
                        + "   " + mcLine.getTableName() + " AS linie, "
                        + "   " + mcStation.getTableName() + " AS von, "
                        + "   " + mcStation.getTableName() + " AS bis, "
                        + "   " + mcRoute.getTableName() + " AS route "
                        + "WHERE "
                        + "   wk_fg.teile = wk_fg_teile.wk_fg_reference AND "
                        + "   wk_fg_teile.teil = wk_teil.id AND "
                        + "   wk_teil.linie = linie." + LinearReferencingConstants.PROP_ID + " AND "
                        + "   linie." + LinearReferencingConstants.PROP_STATIONLINIE_FROM + " = von."
                        + LinearReferencingConstants.PROP_ID + " AND "
                        + "   linie." + LinearReferencingConstants.PROP_STATIONLINIE_TO + " = bis."
                        + LinearReferencingConstants.PROP_ID + " AND "
                        + "   von.route = route." + LinearReferencingConstants.PROP_ID + " AND "
                        + "   route." + LinearReferencingConstants.PROP_ROUTE_GWK + " = " + Long.toString(gwk)
                        + " AND ( "
                        + "      (von." + LinearReferencingConstants.PROP_STATION_VALUE + " <= " + Double.toString(wert)
                        + " AND bis." + LinearReferencingConstants.PROP_STATION_VALUE + " >= "
                        + Double.toString(wert) + ") OR "
                        + "      (bis." + LinearReferencingConstants.PROP_STATION_VALUE + " <= " + Double.toString(wert)
                        + " AND von." + LinearReferencingConstants.PROP_STATION_VALUE + " >= "
                        + Double.toString(wert) + ") "
                        + "   ) "
                        + ";";

            queryWkSg = "SELECT "
                        + "   " + mcWkSg.getID() + ", "
                        + "   wk_sg." + mcWkSg.getPrimaryKey() + " "
                        + "FROM "
                        + "   " + mcWkSg.getTableName() + " AS wk_sg, "
                        + "   " + mcGeom.getTableName() + " AS geom_sg, "
                        + "   ( "
                        + "      SELECT "
                        + "         querbauwerke.id AS id, "
                        + "         station_von.wert AS wert, "
                        + "         route.gwk AS gwk, "
                        + "         ST_Line_Substring( "
                        + "            geom_route.geo_field, "
                        + "            (case when station_von.wert < station_bis.wert then station_von.wert else station_bis.wert end ) / length2d(geom_route.geo_field), "
                        + "            (case when station_von.wert < station_bis.wert then station_bis.wert else station_von.wert end ) / length2d(geom_route.geo_field) "
                        + "         ) AS geom "
                        + "      FROM "
                        + "         " + mcQuerbauwerke.getTableName() + " AS querbauwerke, "
                        + "         " + mcStation.getTableName() + " AS station_von, "
                        + "         " + mcStation.getTableName() + " AS station_bis, "
                        + "         " + mcRoute.getTableName() + " AS route, "
                        + "         " + mcGeom.getTableName() + " AS geom_route "
                        + "      WHERE "
                        + "         querbauwerke.stat09 = station_von.id AND "
                        + "         querbauwerke.stat09_bis = station_bis.id AND "
                        + "         station_von.route = route.id AND "
                        + "         route.geom = geom_route.id "
                        + "   ) AS qbw, "
                        + "   ( "
                        + "      SELECT "
                        + "         querbauwerke.id AS id, "
                        + "         ST_Extent(geom_route.geo_field) AS geom "
                        + "      FROM "
                        + "         " + mcQuerbauwerke.getTableName() + " AS querbauwerke, "
                        + "         " + mcStation.getTableName() + " AS station_von, "
                        + "         " + mcRoute.getTableName() + " AS route, "
                        + "         " + mcGeom.getTableName() + " AS geom_route "
                        + "      WHERE "
                        + "         querbauwerke.stat09 = station_von.id AND "
                        + "         station_von.route = route.id AND "
                        + "         route.geom = geom_route.id "
                        + "      GROUP BY querbauwerke.id "
                        + "   ) AS qbw_ext "
                        + "WHERE "
                        + "   qbw.id = " + id + " AND "
                        + "   wk_sg.geom = geom_sg.id AND "
                        + "   qbw.id = qbw_ext.id AND "
                        + "   geom_sg.geo_field && qbw_ext.geom AND "
                        + "   ST_Intersects( "
                        + "      geom_sg.geo_field, "
                        + "      qbw.geom "
                        + "   ) "
                        + ";";
        }
        try {
            final MetaObject[] mosWkFg;
            final MetaObject[] mosWkSg;
            if (LOG.isDebugEnabled()) {
                LOG.debug("queryWkFg  => " + queryWkFg);
                LOG.debug("queryWkSg  => " + queryWkSg);
            }
            mosWkFg = SessionManager.getProxy().getMetaObjectByQuery(queryWkFg, 0);
            mosWkSg = SessionManager.getProxy().getMetaObjectByQuery(queryWkSg, 0);

            final MetaObject moWkFg;
            final MetaObject moWkSg;
            if ((mosWkFg != null) && (mosWkFg.length > 0)) {
                moWkFg = mosWkFg[0];
                final String wkK = (String)moWkFg.getAttributeByFieldName("wk_k").getValue();
                querbauwerkePanTwo.setWaKoerper(wkK);
                querbauwerkePanTwo.setMoWk(moWkFg);
                querbauwerkePanTwo.setKategorie(QuerbauwerkePanTwo.Kategorie.Fliessgewaesser);
            } else if ((mosWkSg != null) && (mosWkSg.length > 0)) {
                moWkSg = mosWkSg[0];
                final String wkK = (String)moWkSg.getAttributeByFieldName("wk_k").getValue();
                querbauwerkePanTwo.setWaKoerper(wkK);
                querbauwerkePanTwo.setMoWk(moWkSg);
                querbauwerkePanTwo.setKategorie(QuerbauwerkePanTwo.Kategorie.Standgewaesser);
            } else {
                querbauwerkePanTwo.setWaKoerper(null);
                querbauwerkePanTwo.setMoWk(null);
                querbauwerkePanTwo.setKategorie(null);
            }
        } catch (ConnectionException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while fetching metaobject", ex);
            }
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while autosetting wa_koerper", ex);
            }
        }
    }

    /**
     * DOCUMENT ME!
     */
    private void updateQbwId() {
        try {
            final CidsBean stat09 = (CidsBean)cidsBean.getProperty("stat09");
            final String wert =
                new DecimalFormat("#.#").format((Double)stat09.getProperty(
                        LinearReferencingConstants.PROP_STATION_VALUE));
            final CidsBean route = (CidsBean)stat09.getProperty(LinearReferencingConstants.PROP_STATION_ROUTE);
            final String gwk = String.valueOf(route.getProperty(LinearReferencingConstants.PROP_ROUTE_GWK));
            querbauwerkePanOne.setQbwId(gwk + "@" + wert);
        } catch (Exception ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("error while auto-setting qbw_id", ex);
            }
        }
    }

    @Override
    public void dispose() {
        querbauwerkePanOne.dispose();
        querbauwerkePanTwo.dispose();
        querbauwerkePanThree.dispose();
        querbauwerkePanFour.dispose();
        querbauwerkePanFive.dispose();
        querbauwerkePanSix.dispose();
    }

    @Override
    public String getTitle() {
        return String.valueOf(cidsBean);
    }

    @Override
    public void setTitle(final String title) {
        // NOP
    }

    @Override
    public void editorClosed(final EditorClosedEvent event) {
        // NOP
    }

    @Override
    public boolean prepareForSave() {
        if (cidsBean != null) {
            try {
                cidsBean.setProperty("av_user", SessionManager.getSession().getUser().toString());
                cidsBean.setProperty("av_time", new java.sql.Timestamp(System.currentTimeMillis()));
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }
        }
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(final String[] args) throws Exception {
        new WrrlEditorTester("Querbauwerke", QuerbauwerkeEditor.class, CidsBeanSupport.DOMAIN_NAME).run();
    }
}
