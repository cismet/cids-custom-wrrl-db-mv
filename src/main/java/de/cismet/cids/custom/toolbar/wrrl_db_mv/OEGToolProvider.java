/***************************************************
 *
 * cismet GmbH, Saarbruecken, Germany
 *
 *              ... and it just works.
 *
 ****************************************************/
/*
 *  Copyright (C) 2011 thorsten
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
package de.cismet.cids.custom.toolbar.wrrl_db_mv;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;
import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.search.builtin.GeoSearch;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

import java.awt.Cursor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.JToggleButton;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.features.Feature;
import de.cismet.cismap.commons.features.FeatureGroup;
import de.cismet.cismap.commons.features.FeatureGroups;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.ToolbarComponentDescription;
import de.cismet.cismap.commons.gui.ToolbarComponentsProvider;
import de.cismet.cismap.commons.interaction.CismapBroker;

import de.cismet.cismap.navigatorplugin.CidsFeature;

import de.cismet.tools.collections.TypeSafeCollections;
import de.cismet.tools.gui.StaticSwingTools;
import java.awt.EventQueue;
import java.util.Vector;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = ToolbarComponentsProvider.class)
public class OEGToolProvider implements ToolbarComponentsProvider {

    //~ Static fields/initializers ---------------------------------------------
    public static final String OEG_GESAMT = "OEG_GESAMT";
    public static final String OEG_EINZELN = "OEG_EINZELN";
    //~ Instance fields --------------------------------------------------------
    OEGWaitDialog waiting = null;
    private final List<ToolbarComponentDescription> toolbarComponents;
    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    ArrayList<MetaClass> oegGesamt = null;
    ArrayList<MetaClass> oegEinzeln = null;
    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new OEGToolProvider object.
     */
    public OEGToolProvider() {
        final List<ToolbarComponentDescription> preparationList = new ArrayList<ToolbarComponentDescription>();
        final JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setMaximumSize(new java.awt.Dimension(2, 32767));
        sep.setPreferredSize(new java.awt.Dimension(2, 10));
        sep.setName("oeg-sep");

        final JToggleButton cmdOEGGesamt = new JToggleButton();
        cmdOEGGesamt.setText("OEG (Gesamt)");
        cmdOEGGesamt.setToolTipText("OEG Tool (Gesamt)");
        cmdOEGGesamt.setName("oeg_tool_gesamt");
        cmdOEGGesamt.setBorderPainted(false);
        cmdOEGGesamt.setFocusable(false);
        // setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/icons/alkisframeprint.png")));
        cmdOEGGesamt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdOEGGesamt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdOEGGesamt.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                try {
                    if (CismapBroker.getInstance().getMappingComponent().getInputListener(
                            OEGToolProvider.OEG_GESAMT)
                            == null) {
                        CismapBroker.getInstance().getMappingComponent().addInputListener(OEGToolProvider.OEG_GESAMT, new PBasicInputEventHandler() {

                            @Override
                            public void mouseClicked(final PInputEvent event) {
                                OegSearch(OEG_GESAMT, event);

                            }
                        });
                        CismapBroker.getInstance().getMappingComponent().putCursor(OEGToolProvider.OEG_GESAMT, new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                    CismapBroker.getInstance().getMappingComponent().setInteractionMode(OEGToolProvider.OEG_GESAMT);
                } catch (Exception e) {
                    log.error("Fehler beim Aufruf des OEG-Tools", e);
                }
            }
        });

        final JToggleButton cmdOEGEinzel = new JToggleButton();
        cmdOEGEinzel.setText("OEG (Einzeln)");
        cmdOEGEinzel.setToolTipText("OEG Tool (Einzeln)");
        cmdOEGEinzel.setName("oeg_tool_einzeln");
        cmdOEGEinzel.setBorderPainted(false);
        cmdOEGEinzel.setFocusable(false);
        // setIcon(new
        // javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/icons/alkisframeprint.png")));
        cmdOEGEinzel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cmdOEGEinzel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cmdOEGEinzel.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                if (CismapBroker.getInstance().getMappingComponent().getInputListener(OEGToolProvider.OEG_EINZELN)
                        == null) {
                    CismapBroker.getInstance().getMappingComponent().addInputListener(OEGToolProvider.OEG_EINZELN, new PBasicInputEventHandler() {

                        @Override
                        public void mouseClicked(final PInputEvent event) {
                            super.mouseClicked(event);
                            OegSearch(OEG_EINZELN, event);
                        }
                    });
                    CismapBroker.getInstance().getMappingComponent().putCursor(OEGToolProvider.OEG_EINZELN, new Cursor(Cursor.CROSSHAIR_CURSOR));
                }
                CismapBroker.getInstance().getMappingComponent().setInteractionMode(OEGToolProvider.OEG_EINZELN);
            }
        });

        CismapBroker.getInstance().getMappingComponent().getInteractionButtonGroup().add(cmdOEGGesamt);
        CismapBroker.getInstance().getMappingComponent().getInteractionButtonGroup().add(cmdOEGEinzel);

        preparationList.add(new ToolbarComponentDescription(
                "tlbMain",
                sep,
                ToolbarPositionHint.AFTER,
                "cmdClipboard"));
        preparationList.add(new ToolbarComponentDescription(
                "tlbMain",
                cmdOEGGesamt,
                ToolbarPositionHint.AFTER,
                "oeg-sep"));
        preparationList.add(new ToolbarComponentDescription(
                "tlbMain",
                cmdOEGEinzel,
                ToolbarPositionHint.AFTER,
                "oeg_tool_gesamt"));
        this.toolbarComponents = Collections.unmodifiableList(preparationList);
    }

    //~ Methods ----------------------------------------------------------------
    @Override
    public String getPluginName() {
        return "OEGTool";
    }

    @Override
    public Collection<ToolbarComponentDescription> getToolbarComponents() {
        return toolbarComponents;
    }

    private void OegSearch(final String type, final PInputEvent event) {
        final MappingComponent mc = CismapBroker.getInstance().getMappingComponent();


        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                waiting = new OEGWaitDialog(
                        StaticSwingTools.getFirstParentFrame(mc),
                        true);
                waiting.pack();
                waiting.setLocationRelativeTo(mc);
                waiting.setVisible(true);
            }
        });

        de.cismet.tools.CismetThreadPool.execute(
                new javax.swing.SwingWorker<Collection<Feature>, Void>() {

                    @Override
                    protected Collection<Feature> doInBackground()
                            throws Exception {
                        final Point geom = mc.getPointGeometryFromPInputEvent(
                                event);
                        final Geometry transformed = CrsTransformer.transformToDefaultCrs(geom);
                        // Damits auch mit -1 funzt:
                        transformed.setSRID(
                                CismapBroker.getInstance().getDefaultCrsAlias());

                        final GeoSearch gs = new GeoSearch(transformed);

                        if (type.equals(OEG_GESAMT)) {
                            if (oegGesamt == null) {
                                oegGesamt = new ArrayList<MetaClass>();
                                try {
                                    oegGesamt.add(
                                            CidsBean.getMetaClassFromTableName(
                                            "WRRL_DB_MV",
                                            "oeg_einzugsgebiet"));
                                    oegGesamt.add(
                                            CidsBean.getMetaClassFromTableName(
                                            "WRRL_DB_MV",
                                            "oeg_kummuliert"));
                                } catch (Exception exception) {
                                    log.error(
                                            "Fehler beim Setzen der KLassen",
                                            exception);
                                }
                            }
                            gs.setValidClasses(oegGesamt);
                        } else {
                            if (oegEinzeln == null) {
                                oegEinzeln = new ArrayList<MetaClass>();
                                try {
                                    oegEinzeln.add(
                                            CidsBean.getMetaClassFromTableName(
                                            "WRRL_DB_MV",
                                            "oeg_einzugsgebiet"));
                                    oegEinzeln.add(
                                            CidsBean.getMetaClassFromTableName(
                                            "WRRL_DB_MV",
                                            "oeg_kummuliert_ref"));
                                } catch (Exception exception) {
                                    log.error(
                                            "Fehler beim Setzen der KLassen",
                                            exception);
                                }
                            }

                            gs.setValidClasses(oegEinzeln);
                        }
                        final ArrayList<Feature> cfs = new ArrayList<Feature>();
                        final Collection res = SessionManager.getProxy().customServerSearch(
                                SessionManager.getSession().getUser(),
                                gs);
                        for (final Object o : res) {
                            final MetaObjectNode mon = (MetaObjectNode) o;
                            final MetaObject mo = SessionManager.getProxy().getMetaObject(
                                    mon.getObjectId(),
                                    mon.getClassId(),
                                    mon.getDomain());
                            final CidsFeature cf = new CidsFeature(mo);
                            cfs.add(cf);
                        }
                        return cfs;
                    }

                    @Override
                    protected void done() {
                        try {
                            final Collection<Feature> result = get();

                            Collection<Feature> expandedResults=new ArrayList<Feature>((int)(result.size()*1.6));
                            for (Feature f : result) {
                                if (f instanceof FeatureGroup) {
                                    final List<Feature> allFeaturesToAdd = new ArrayList<Feature>(FeatureGroups.expandAll((FeatureGroup) f));
                                    expandedResults.addAll(allFeaturesToAdd);
                                }
                                expandedResults.add(f);

                            }

                            mc.getFeatureCollection().substituteFeatures(expandedResults);

                            if (!mc.isFixedMapExtent()) {
                                mc.zoomToFeatureCollection(mc.isFixedMapScale());
                            }
                        } catch (Exception e) {
                            log.error("Exception in Background Thread", e);
                        }
                        waiting.dispose();
                    }
                });
    }
}
