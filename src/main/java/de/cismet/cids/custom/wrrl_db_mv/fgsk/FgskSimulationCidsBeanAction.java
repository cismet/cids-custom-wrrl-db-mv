/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.wrrl_db_mv.fgsk;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.types.treenode.DefaultMetaTreeNode;
import Sirius.navigator.types.treenode.ObjectTreeNode;
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.middleware.types.MetaObjectNode;
import Sirius.server.newuser.permission.Policy;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

import de.cismet.cids.custom.treeicons.wrrl_db_mv.SimMassnahmenGruppeIconFactory;
import de.cismet.cids.custom.wrrl_db_mv.util.CidsBeanSupport;
import de.cismet.cids.custom.wrrl_db_mv.util.UIUtil;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.utils.abstracts.AbstractCidsBeanAction;

import de.cismet.tools.gui.StaticSwingTools;
import de.cismet.tools.gui.WaitingDialogThread;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class FgskSimulationCidsBeanAction extends AbstractCidsBeanAction {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(FgskSimulationCExtProvider.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FgskSimulationCidsBeanAction object.
     */
    public FgskSimulationCidsBeanAction() {
        super(
            "Simulation duplizieren",
            new ImageIcon(
                FgskSimulationCidsBeanAction.class.getResource(
                    "/de/cismet/cids/custom/wrrl_db_mv/fgsk/icon-copy.png")));
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Frame f = ((e.getSource() instanceof Component)
                ? StaticSwingTools.getParentFrame((Component)e.getSource()) : null);
        final WaitingDialogThread<CidsBean> wdt = new WaitingDialogThread<CidsBean>(
                f,
                true,
                "Erstelle Duplikat",
                null,
                100) {

                @Override
                protected CidsBean doInBackground() throws Exception {
                    final CidsBean origBean = getCidsBean();
                    final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName("simulation");
                    newBean.setProperty("name", String.valueOf(origBean.getProperty("name")) + " Variante");
                    newBean.setProperty("beschreibung", String.valueOf(origBean.getProperty("beschreibung")));
                    newBean.setProperty("wk_key", String.valueOf(origBean.getProperty("wk_key")));
                    newBean.setProperty("read_only", Boolean.FALSE);
                    final List<CidsBean> origMassn = origBean.getBeanCollectionProperty(
                            "angewendete_simulationsmassnahmen");
                    final List<CidsBean> massn = newBean.getBeanCollectionProperty("angewendete_simulationsmassnahmen");

                    if (origMassn != null) {
                        for (final CidsBean origMassnBean : origMassn) {
                            massn.add(CidsBeanSupport.cloneCidsBean(origMassnBean, false));
                        }
                    }

                    return newBean.persist();
                }

                @Override
                protected void done() {
                    try {
                        final CidsBean bean = get();
                        final MetaObjectNode metaObjectNode = new MetaObjectNode(
                                -1,
                                SessionManager.getSession().getUser().getDomain(),
                                bean.getMetaObject(),
                                null,
                                null,
                                true,
                                Policy.createWIKIPolicy(),
                                -1,
                                null,
                                false);
                        final DefaultMetaTreeNode metaTreeNode = new ObjectTreeNode(metaObjectNode);
                        ComponentRegistry.getRegistry().showComponent(ComponentRegistry.ATTRIBUTE_EDITOR);
                        ComponentRegistry.getRegistry().getAttributeEditor().setTreeNode(metaTreeNode);
                        TreePath treePath = ComponentRegistry.getRegistry().getCatalogueTree().getSelectionPath();

                        if (treePath != null) {
                            treePath = treePath.getParentPath();
                        }

                        UIUtil.refreshTree(treePath);
                    } catch (Exception ex) {
                        LOG.error("Error while cloning bean.", ex);
                    }
                }
            };

        wdt.start();
    }
}
